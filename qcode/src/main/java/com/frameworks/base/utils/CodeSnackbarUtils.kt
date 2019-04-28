package com.frameworks.base.utils

import android.app.Activity
import com.frameworks.R
import com.irozon.sneaker.Sneaker

class CodeSnackbarUtils {

    enum class SneakerType{
        TYPE_DEFAULT,     //默认
        TYPE_ERORR,       //错误提示
        TYPE_SUCCES,      //成功提示
        TYPE_WARNING,     //提示
        TYPE_TOAST,       //TOAST提示
    }

    companion object {
        var codeSnackbarUtils : CodeSnackbarUtils ? =null
                get() = CodeSnackbarUtils()
    }


    open fun showSneaker(activity:Activity,type: SneakerType, msg: String) {
        when (type) {
            SneakerType.TYPE_DEFAULT ->    //默认
                Sneaker.with(activity)
                        .setDuration(1000)
                        .setMessage(msg, R.color.white)
                        .sneak(R.color.gray_c6)

            SneakerType.TYPE_ERORR ->     //错误
                Sneaker.with(activity)
                        .setDuration(1000)
                        .setMessage(msg, R.color.white)
                        .sneak(android.graphics.Color.parseColor("#F56262"))

            SneakerType.TYPE_WARNING ->     //提示

                Sneaker.with(activity)
                        .setDuration(1000)
                        .setMessage(msg, R.color.white)
                        .sneakWarning()

            SneakerType.TYPE_WARNING ->    //成功

                Sneaker.with(activity)
                        .setDuration(1000)
                        .setMessage(msg, R.color.white)
                        .sneak(android.graphics.Color.parseColor("#628CF5"))
            SneakerType.TYPE_TOAST ->
                CodeToastUtils.showToast(activity, msg)
        }
    }

}