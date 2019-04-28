package com.xiangjiale.zaoplanet.utils

import com.frameworks.base.utils.CodeLogUtils
import com.google.gson.Gson
import com.xiangjiale.zaoplanet.BuildConfig
import com.xiangjiale.zaoplanet.config.AppConfig
import com.xiangjiale.zaoplanet.utils.aesutils.AESUtil
import java.util.HashMap

class RequestUtils{

    companion object {
        var SERVERTIME: Long = 0//服务器时间和本地时间差

        var headMap : HashMap<String,Any>? =null
        fun getVersion(): String {
            return BuildConfig.VERSION_NAME
        }

        val koten : String ? get() = UserDataUtils.intance!!.getUserData()?.accessToken ?:""


        fun getSignValue() : String {
            if (headMap == null) {
                headMap      = HashMap<String, Any>()
            }
            val millis       = System.currentTimeMillis() + SERVERTIME
            val sss          = millis.toString().substring(0, 10) + "." + millis.toString().substring(10, 13)

            headMap?.put("expire", sss)

            val signValue    = Gson().toJson(headMap)
            val aesSignValue = AESUtil.Encrypt(signValue, AppConfig.SECRE_KEY)

            CodeLogUtils.debugInfo("okhttp", "sss=$sss")
            CodeLogUtils.debugInfo("okhttp", "signValue=$signValue")
            CodeLogUtils.debugInfo("okhttp", "aesSignValue=$aesSignValue")
            return aesSignValue
        }

        fun setServertime(data:Long){
            var datas               = data * 1000
            var millis              = System.currentTimeMillis()
            RequestUtils.SERVERTIME = datas - millis
        }

        fun getCurrentMillis():Long{
            return SERVERTIME + System.currentTimeMillis()
        }


    }


}