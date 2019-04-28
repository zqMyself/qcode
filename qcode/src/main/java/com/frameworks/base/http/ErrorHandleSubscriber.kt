package com.frameworks.base.http

import android.text.TextUtils
import com.frameworks.base.core.BaseData
import com.frameworks.base.core.BaseEntry
import com.frameworks.base.interfaces.IdentificationCode
import com.google.gson.JsonParseException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable
import io.reactivex.exceptions.CompositeException
import okhttp3.ResponseBody
import org.json.JSONException
import retrofit2.HttpException
import java.io.IOException
import java.net.ConnectException
import java.net.SocketTimeoutException
import java.net.UnknownHostException

abstract class ErrorHandleSubscriber<T> : Observer<T>{


    companion object {
        const val EX_CONNECT_STR = "加载失败，请重试"//连接服务器异常
        const val EX_SOCKET_TIME_OUT_STR = "请求超时，请检查您的网络状态"//超时了
        const val EX_HTTP_STR = "网络错误，请检查您的网络设置"//连接不到服务器
        const val EX_IO_STR = "连接失败"
        const val EX_PARSE_STR = "数据解析失败"
        const val EX_UNKNOW_STR = "未知错误"

        fun isException(msg: String): Boolean {
            return if (TextUtils.isEmpty(msg)) false else msg == EX_CONNECT_STR || msg == EX_SOCKET_TIME_OUT_STR || msg == EX_HTTP_STR || msg == EX_PARSE_STR || EX_UNKNOW_STR == msg || EX_IO_STR == msg
        }
    }

    override fun onNext(baseentry: T) {
        if (baseentry is BaseEntry<*>) {
            when (baseentry.status) {
                IdentificationCode.SUCCESS ->
                    if (baseentry.data != null) {
                        if (baseentry.data is List<*>) {
                            if ((baseentry.data as List<*>).size > 0) {
                                onSuccess(baseentry)
                            } else if (baseentry.source != io.rx_cache2.Source.CLOUD){
                                onRequestCloud()
                            }else {
                                onEmpty(baseentry.msg)
                            }
                        } else if (baseentry.data is ArrayList<*>) {
                            if ((baseentry.data as ArrayList<*>).size > 0) {
                                onSuccess(baseentry)
                            }else if (baseentry.source != io.rx_cache2.Source.CLOUD){
                                onRequestCloud()
                            }else {
                                onEmpty(baseentry.msg)
                            }
                        } else if (baseentry.data is BaseData<*>) {
                            if ((baseentry.data as BaseData<*>).rows!!.isNotEmpty()) {
                                onSuccess(baseentry)
                            }else if (baseentry.source != io.rx_cache2.Source.CLOUD){
                                onRequestCloud()
                            }else {
                                onEmpty(baseentry.msg)
                            }
                        } else {
                            onSuccess(baseentry)
                        }
                    } else {
                        onException(baseentry.status, baseentry.msg)
                    }
                IdentificationCode.CODE_400 ->{
                    onException(baseentry.status, baseentry.msg)
                }
                IdentificationCode.CODE_500 -> onException(baseentry.status, baseentry.msg)
                IdentificationCode.CODE_202 -> onException(baseentry.status, "" + IdentificationCode.CODE_202)
                IdentificationCode.CODE__100 //未登录
                -> onException(baseentry.status, "" + IdentificationCode.CODE__100)
                IdentificationCode.CODE_105 -> { //时间戳过期
//                    var data = java.lang.Long.valueOf(baseentry.msg)
//                    RequestUtils.setServertime(data)
                }
                IdentificationCode.CODE__400 //签名过期
                -> {
                    onException(baseentry.status, baseentry.msg)
                    //                var data = java.lang.Long.valueOf(baseentry.msg)
                    //                RequestUtils.setServertime(data)
                }

                else -> onException(baseentry.status, baseentry.msg)
            }
        }else if (baseentry is ResponseBody){
            onSuccess(baseentry)
        }
    }

    override fun onError(e: Throwable) {
        var str =""
        var code = 0
        if (e is ConnectException) {
            str = EX_CONNECT_STR
            code =  8888
        } else if (e is SocketTimeoutException) {
            str = EX_SOCKET_TIME_OUT_STR
            code = 8888
        } else if (e is HttpException) {//连接不到服务器
            str = EX_HTTP_STR
            code = 8888
        } else if (e is IOException) {
            str = EX_IO_STR
            code = 8888
        } else if (e is JsonParseException || e is JSONException) {
            str = EX_PARSE_STR
        } else if(e is UnknownHostException){
            str = EX_HTTP_STR
            code = 8888
        }else if(e is CompositeException){
            str = EX_HTTP_STR
            code = 8888
        }
        e.printStackTrace()
        onException(code,str)
    }

    override fun onComplete() { }
    override fun onSubscribe(d: Disposable) { }

    abstract fun onException(code: Int, str: String)
    abstract fun onSuccess(t: T)
    abstract fun onEmpty(str: String)
     open fun onRequestCloud(){}

}