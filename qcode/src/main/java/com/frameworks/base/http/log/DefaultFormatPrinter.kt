/*
 * Copyright 2018 JessYan
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

import android.text.TextUtils

import com.frameworks.base.utils.CodeCharacterHandler
import com.frameworks.base.utils.CodeLogUtils

import okhttp3.MediaType
import okhttp3.Request

/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     :对 OkHttp 的请求和响应信息进行更规范和清晰的打印, 此类为框架默认实现, 以默认格式打印信息, 若觉得默认打印格式
 *           并不能满足自己的需求, 可自行扩展自己理想的打印格式
 * version  : 1.0
 * </pre>
 *
 */
class DefaultFormatPrinter : FormatPrinter {

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 可以解析的情况
     *
     * @param request
     * @param bodyString
     */
    override fun printJsonRequest(request: Request, bodyString: String) {
        val requestBody     = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString
        val tag = getTag(true)

        CodeLogUtils.debugInfo(tag, REQUEST_UP_LINE)
        logLines(tag, arrayOf(URL_TAG + request.url()), false)
        logLines(tag, getRequest(request), true)
        logLines(tag, requestBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), true)
        CodeLogUtils.debugInfo(tag, END_LINE)
    }

    /**
     * 打印网络请求信息, 当网络请求时 {[okhttp3.RequestBody]} 为 `null` 或不可解析的情况
     *
     * @param request
     */
    override fun printFileRequest(request: Request) {
        val tag             = getTag(true)

        CodeLogUtils.debugInfo(tag, REQUEST_UP_LINE)
        logLines(tag, arrayOf(URL_TAG + request.url()), false)
        logLines(tag, getRequest(request), true)
        logLines(tag, OMITTED_REQUEST, true)
        CodeLogUtils.debugInfo(tag, END_LINE)
    }

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 可以解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param contentType  服务器返回数据的数据类型
     * @param bodyString   服务器返回的数据(已解析)
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    override fun printJsonResponse(chainMs: Long, isSuccessful: Boolean, code: Int, headers: String, contentType: MediaType,
                                   bodyString: String, segments: List<String>, message: String, responseUrl: String) {
        var bodyString      = bodyString
        bodyString = if (RequestInterceptor.isJson(contentType))
            CodeCharacterHandler.jsonFormat(bodyString)
        else if (RequestInterceptor.isXml(contentType)) CodeCharacterHandler.xmlFormat(bodyString) else bodyString

        val responseBody    = LINE_SEPARATOR + BODY_TAG + LINE_SEPARATOR + bodyString
        val tag = getTag(false)
        val urlLine = arrayOf(URL_TAG + responseUrl, N)

        CodeLogUtils.debugInfo(tag, RESPONSE_UP_LINE)
        logLines(tag, urlLine, true)
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true)
        logLines(tag, responseBody.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray(), true)
        CodeLogUtils.debugInfo(tag, END_LINE)
    }

    /**
     * 打印网络响应信息, 当网络响应时 {[okhttp3.ResponseBody]} 为 `null` 或不可解析的情况
     *
     * @param chainMs      服务器响应耗时(单位毫秒)
     * @param isSuccessful 请求是否成功
     * @param code         响应码
     * @param headers      请求头
     * @param segments     域名后面的资源地址
     * @param message      响应信息
     * @param responseUrl  请求地址
     */
    override fun printFileResponse(chainMs: Long, isSuccessful: Boolean, code: Int, headers: String,
                                   segments: List<String>, message: String, responseUrl: String) {
        val tag = getTag(false)
        val urlLine = arrayOf(URL_TAG + responseUrl, N)

        CodeLogUtils.debugInfo(tag, RESPONSE_UP_LINE)
        logLines(tag, urlLine, true)
        logLines(tag, getResponse(headers, chainMs, code, isSuccessful, segments, message), true)
        logLines(tag, OMITTED_RESPONSE, true)
        CodeLogUtils.debugInfo(tag, END_LINE)
    }

    companion object {
        private val TAG = "ArmsHttpLog"
        private val LINE_SEPARATOR = System.getProperty("line.separator")
        private val DOUBLE_SEPARATOR = LINE_SEPARATOR!! + LINE_SEPARATOR

        private val OMITTED_RESPONSE = arrayOf(LINE_SEPARATOR, "Omitted response body")
        private val OMITTED_REQUEST = arrayOf(LINE_SEPARATOR, "Omitted request body")

        private val N = "\n"
        private val T = "\t"
        private val REQUEST_UP_LINE = "   ┌────── Request ────────────────────────────────────────────────────────────────────────"
        private val END_LINE = "   └───────────────────────────────────────────────────────────────────────────────────────"
        private val RESPONSE_UP_LINE = "   ┌────── Response ───────────────────────────────────────────────────────────────────────"
        private val BODY_TAG = "Body:"
        private val URL_TAG = "URL: "
        private val METHOD_TAG = "Method: @"
        private val HEADERS_TAG = "Headers:"
        private val STATUS_CODE_TAG = "Status Code: "
        private val RECEIVED_TAG = "Received in: "
        private val CORNER_UP = "┌ "
        private val CORNER_BOTTOM = "└ "
        private val CENTER_LINE = "├ "
        private val DEFAULT_LINE = "│ "


        private fun isEmpty(line: String): Boolean {
            return TextUtils.isEmpty(line) || N == line || T == line || TextUtils.isEmpty(line.trim { it <= ' ' })
        }


        /**
         * 对 `lines` 中的信息进行逐行打印
         *
         * @param tag
         * @param lines
         * @param withLineSize 为 `true` 时, 每行的信息长度不会超过110, 超过则自动换行
         */
        private fun logLines(tag: String, lines: Array<String>, withLineSize: Boolean) {
            for (line in lines) {
                val lineLength = line.length
                val MAX_LONG_SIZE = if (withLineSize) 110 else lineLength
                for (i in 0..lineLength / MAX_LONG_SIZE) {
                    val start = i * MAX_LONG_SIZE
                    var end = (i + 1) * MAX_LONG_SIZE
                    end = if (end > line.length) line.length else end
                    CodeLogUtils.debugInfo(resolveTag(tag), DEFAULT_LINE + line.substring(start, end))
                }
            }
        }

        private val last = object : ThreadLocal<Int>() {
            override fun initialValue(): Int {
                return 0
            }
        }

        private val ARMS = arrayOf("-A-", "-R-", "-M-", "-S-")

        private fun computeKey(): String {
            if (last.get() >= 4) {
                last.set(0)
            }
            val s = ARMS[last.get()]
            last.set(last.get()!! + 1)
            return s
        }

        /**
         * 此方法是为了解决在 AndroidStudio v3.1 以上 Logcat 输出的日志无法对齐的问题
         *
         *
         * 此问题引起的原因, 据 JessYan 猜测, 可能是因为 AndroidStudio v3.1 以上将极短时间内以相同 tag 输出多次的 log 自动合并为一次输出
         * 导致本来对称的输出日志, 出现不对称的问题
         * AndroidStudio v3.1 此次对输出日志的优化, 不小心使市面上所有具有日志格式化输出功能的日志框架无法正常工作
         * 现在暂时能想到的解决方案有两个: 1. 改变每行的 tag (每行 tag 都加一个可变化的 token) 2. 延迟每行日志打印的间隔时间
         *
         *
         * [.resolveTag] 使用第一种解决方案
         *
         * @param tag
         */
        private fun resolveTag(tag: String): String {
            return computeKey() + tag
        }


        private fun getRequest(request: Request): Array<String> {
            val log: String
            val header = request.headers().toString()
            log = METHOD_TAG + request.method() + DOUBLE_SEPARATOR +
                    if (isEmpty(header)) "" else HEADERS_TAG + LINE_SEPARATOR + dotHeaders(header)
            return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun getResponse(header: String, tookMs: Long, code: Int, isSuccessful: Boolean,
                                segments: List<String>, message: String): Array<String> {
            val log: String
            val segmentString = slashSegments(segments)
            log = ((if (!TextUtils.isEmpty(segmentString)) "$segmentString - " else "") + "is success : "
                    + isSuccessful + " - " + RECEIVED_TAG + tookMs + "ms" + DOUBLE_SEPARATOR + STATUS_CODE_TAG +
                    code + " / " + message + DOUBLE_SEPARATOR + if (isEmpty(header))
                ""
            else
                HEADERS_TAG + LINE_SEPARATOR +
                        dotHeaders(header))
            return log.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
        }

        private fun slashSegments(segments: List<String>): String {
            val segmentString = StringBuilder()
            for (segment in segments) {
                segmentString.append("/").append(segment)
            }
            return segmentString.toString()
        }

        /**
         * 对 `header` 按规定的格式进行处理
         *
         * @param header
         * @return
         */
        private fun dotHeaders(header: String): String {
            val headers = header.split(LINE_SEPARATOR.toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            val builder = StringBuilder()
            var tag = "─ "
            if (headers.size > 1) {
                for (i in headers.indices) {
                    if (i == 0) {
                        tag = CORNER_UP
                    } else if (i == headers.size - 1) {
                        tag = CORNER_BOTTOM
                    } else {
                        tag = CENTER_LINE
                    }
                    builder.append(tag).append(headers[i]).append("\n")
                }
            } else {
                for (item in headers) {
                    builder.append(tag).append(item).append("\n")
                }
            }
            return builder.toString()
        }


        private fun getTag(isRequest: Boolean): String {
            return if (isRequest) {
                "$TAG-Request"
            } else {
                "$TAG-Response"
            }
        }
    }

}
