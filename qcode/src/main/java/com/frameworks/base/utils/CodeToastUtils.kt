package com.frameworks.base.utils

import android.content.Context
import android.widget.Toast
/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/11
 *     desc     : Toast工具类
 *     version  : 1.0
 * </pre>
 */
object CodeToastUtils {

    private var mToast      :       Toast? = null

    /**
     * 非阻塞试显示Toast,防止出现连续点击Toast时的显示问题
     */
    @JvmOverloads
    fun showToast(context: Context, text: CharSequence, duration: Int = Toast.LENGTH_SHORT) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, duration)
        } else {
            mToast!!.setText(text)
            mToast!!.duration = duration
        }
        mToast!!.show()
    }


}
