/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frameworks.base.http.log

import com.frameworks.base.http.GlobalHttpHandler
import com.frameworks.base.utils.CodeCharacterHandler
import com.frameworks.base.utils.CodeLogUtils
import com.frameworks.base.utils.CodeUrlEncoderUtils
import com.frameworks.base.utils.CodeZipHelper
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.io.UnsupportedEncodingException
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * ================================================
 *
 *
 *
 * Created by JessYan on 7/1/2016.
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */

/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     :解析框架中的网络请求和响应结果,并以日志形式输出,调试神器
 *           可使用 [GlobalConfigModule.Builder.printHttpLogLevel] 控制或关闭日志
 * version  : 1.0
 *</pre>
 *
 */
@Singleton
class RequestInterceptor @Inject
constructor() : Interceptor {
    @Inject
    @JvmField
    internal var mHandler: GlobalHttpHandler? = null
    @Inject
    @JvmField
    internal var mPrinter: FormatPrinter? = null

    @Inject
    @JvmField
    internal var printLevel: Level? = null

    enum class Level {
        /**
         * 不打印log
         */
        NONE,
        /**
         * 只打印请求信息
         */
        REQUEST,
        /**
         * 只打印响应信息
         */
        RESPONSE,
        /**
         * 所有数据全部打印
         */
        ALL
    }

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val logRequest = printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.REQUEST

        if (logRequest) {
            //打印请求信息
            if (request.body() != null && isParseable(request.body()!!.contentType())) {
                if (mPrinter == null)
                CodeLogUtils.debugInfo("logRequest body")


                mPrinter!!.printJsonRequest(request, parseParams(request))
            } else {
                CodeLogUtils.debugInfo("logRequest url")

                mPrinter!!.printFileRequest(request)

            }
        }

        val logResponse = printLevel == Level.ALL || printLevel != Level.NONE && printLevel == Level.RESPONSE

        val t1 = if (logResponse) System.nanoTime() else 0
        val originalResponse: Response
        try {
            originalResponse = chain.proceed(request)
        } catch (e: Exception) {
            CodeLogUtils.warnInfo("Http Error: $e")
            throw e
        }

        val t2 = if (logResponse) System.nanoTime() else 0

        val responseBody = originalResponse.body()

        //打印响应结果
        var bodyString: String? = null
        if (responseBody != null && isParseable(responseBody.contentType())) {
            bodyString = printResult(request, originalResponse, logResponse)
        }

        if (logResponse) {
            val segmentList = request.url().encodedPathSegments()
            val header = originalResponse.headers().toString()
            val code = originalResponse.code()
            val isSuccessful = originalResponse.isSuccessful
            val message = originalResponse.message()
            val url = originalResponse.request().url().toString()

            if (responseBody != null && isParseable(responseBody.contentType())) {
                if (bodyString != null) {
                    mPrinter!!.printJsonResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1), isSuccessful,
                            code, header, responseBody.contentType()!!, bodyString, segmentList, message, url)
                }
            } else {
                mPrinter!!.printFileResponse(TimeUnit.NANOSECONDS.toMillis(t2 - t1),
                        isSuccessful, code, header, segmentList, message, url)
            }

        }

        return if (mHandler != null) mHandler!!.onHttpResultResponse(bodyString!!, chain, originalResponse) else originalResponse

    }

    /**
     * 打印响应结果
     *
     * @param request     [Request]
     * @param response    [Response]
     * @param logResponse 是否打印响应结果
     * @return 解析后的响应结果
     * @throws IOException
     */
    @Throws(IOException::class)
    private fun printResult(request: Request, response: Response, logResponse: Boolean): String? {
        try {
            //读取服务器返回的结果
            val responseBody = response.newBuilder().build().body()
            val source = responseBody!!.source()
            source.request(java.lang.Long.MAX_VALUE) // Buffer the entire body.
            val buffer = source.buffer()

            //获取content的压缩类型
            val encoding = response
                    .headers()
                    .get("Content-Encoding")

            val clone = buffer.clone()

            //解析response content
            return parseContent(responseBody, encoding, clone)
        } catch (e: IOException) {
            e.printStackTrace()
            return "{\"error\": \"" + e.message + "\"}"
        }

    }


    /**
     * 解析服务器响应的内容
     *
     * @param responseBody [ResponseBody]
     * @param encoding     编码类型
     * @param clone        克隆后的服务器响应内容
     * @return 解析后的响应结果
     */
    private fun parseContent(responseBody: ResponseBody, encoding: String?, clone: Buffer): String? {
        var charset: Charset? = Charset.forName("UTF-8")
        val contentType = responseBody.contentType()
        if (contentType != null) {
            charset = contentType.charset(charset)
        }
        return if (encoding != null && encoding.equals("gzip", ignoreCase = true)) {//content使用gzip压缩
            CodeZipHelper.decompressForGzip(clone.readByteArray(), convertCharset(charset.toString()))//解压
        } else if (encoding != null && encoding.equals("zlib", ignoreCase = true)) {//content使用zlib压缩
            CodeZipHelper.decompressToStringForZlib(clone.readByteArray(), convertCharset(charset.toString()))//解压
        } else {//content没有被压缩
            clone.readString(charset!!)
        }
    }

    companion object {

        /**
         * 解析请求服务器的请求参数
         *
         * @param request [Request]
         * @return 解析后的请求信息
         * @throws UnsupportedEncodingException
         */
        @Throws(UnsupportedEncodingException::class)
        fun parseParams(request: Request): String {
            try {
                val body = request.newBuilder().build().body() ?: return ""
                val requestbuffer = Buffer()
                body.writeTo(requestbuffer)
                var charset: Charset? = Charset.forName("UTF-8")
                val contentType = body.contentType()
                if (contentType != null) {
                    charset = contentType.charset(charset)
                }
                var json = requestbuffer.readString(charset!!)
                if (CodeUrlEncoderUtils.hasUrlEncoded(json)) {
                    json = URLDecoder.decode(json, convertCharset(charset.toString()))
                }
                return CodeCharacterHandler.jsonFormat(json)
            } catch (e: IOException) {
                e.printStackTrace()
                return "{\"error\": \"" + e.message + "\"}"
            }

        }

        /**
         * 是否可以解析
         *
         * @param mediaType [MediaType]
         * @return `true` 为可以解析
         */
        fun isParseable(mediaType: MediaType?): Boolean {
            return (isText(mediaType) || isPlain(mediaType)
                    || isJson(mediaType) || isForm(mediaType)
                    || isHtml(mediaType) || isXml(mediaType))
        }

        fun isText(mediaType: MediaType?): Boolean {
            return if (mediaType?.type() == null) false else mediaType.type() == "text"
        }

        fun isPlain(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else mediaType.subtype().toLowerCase().contains("plain")
        }

        fun isJson(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else mediaType.subtype().toLowerCase().contains("json")
        }

        fun isXml(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else mediaType.subtype().toLowerCase().contains("xml")
        }

        fun isHtml(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else mediaType.subtype().toLowerCase().contains("html")
        }

        fun isForm(mediaType: MediaType?): Boolean {
            return if (mediaType?.subtype() == null) false else mediaType.subtype().toLowerCase().contains("x-www-form-urlencoded")
        }

        fun convertCharset(charset: String): String {
            val s = charset
            val i = s.indexOf("[")
            return if (i == -1) s else s.substring(i + 1, s.length - 1)
        }
    }

}
