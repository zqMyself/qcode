package com.frameworks.base.http

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response

class GlobalHttpHandlerImp : GlobalHttpHandler {
    override fun onHttpResultResponse(httpResult: String, chain: Interceptor.Chain, response: Response): Response {
        //不管是否处理,都必须将response返回出去
        return response
    }

    override fun onHttpRequestBefore(chain: Interceptor.Chain, request: Request): Request {
        //不管是否处理,都必须将request返回出去
        return request
    }
}