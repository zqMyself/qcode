package com.frameworks.base.http

/**
 * Created by Administrator on 2017/3/23.
 */

import com.frameworks.BuildConfig
import com.frameworks.base.utils.CodeAppUtils
import com.frameworks.base.utils.CodeDeviceUtils
import okhttp3.FormBody
import okhttp3.Interceptor
import okhttp3.Response

/**
 * 封装公共参数（rememberme和session）
 */
class CommonInterceptor : Interceptor {

//    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {

        var  commonData =  CodeAppUtils.mApplication?.commonData
        var  koten      =  commonData?.koten
        var  signValue  =  commonData?.sign
        var  version  =  commonData?.version


        val original = chain.request()
        //请求定制：添加请求头
        val requestBuilder = original
                .newBuilder()
                .addHeader("Authorization", koten)
                .addHeader("sign", "" + signValue)
        //请求体定制：统一添加token/guid参数
        //插入请求体
        if (original.body() is FormBody) {
            val newFormBody = FormBody.Builder()
            val oidFormBody = original.body() as FormBody?
            for (i in 0 until oidFormBody!!.size()) {
                newFormBody.addEncoded(oidFormBody.encodedName(i), oidFormBody.encodedValue(i))
            }
            newFormBody.add("phoneType", "ANDROID")
            newFormBody.add("version",  version)
            newFormBody.add("brand", CodeDeviceUtils.deviceBrand)
            newFormBody.add("model", CodeDeviceUtils.deviceModel)
            requestBuilder.method(original.method(), newFormBody.build())
        } else {//插入URL
            val httpUrlBuilder = original.url().newBuilder()
            httpUrlBuilder.addQueryParameter("phoneType", "ANDROID")
            httpUrlBuilder.addQueryParameter("version", BuildConfig.VERSION_NAME)
            httpUrlBuilder.addQueryParameter("brand", CodeDeviceUtils.deviceBrand)
            httpUrlBuilder.addQueryParameter("model", CodeDeviceUtils.deviceModel)
            requestBuilder.url(httpUrlBuilder.build())
        }
        val request = requestBuilder.build()
        return chain.proceed(request)

    }

}
