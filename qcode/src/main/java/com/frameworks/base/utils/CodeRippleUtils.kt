package com.frameworks.base.utils

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.RippleDrawable
import android.util.Log
import android.view.View

/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/11
 *     desc     : 点击按钮水波效果工具类
 *     version  : 1.0
 * </pre>
 */
object CodeRippleUtils {

    private var mRipple: RippleDrawable? = null
    private var mTileBackground: Drawable? = null

    private fun newTileBackground(context: Context): Drawable? {
        val attrs = intArrayOf(android.R.attr.selectableItemBackgroundBorderless)
        val ta = context.obtainStyledAttributes(attrs)
        val d = ta.getDrawable(0)
        ta.recycle()
        return d
    }

    private fun setRipple(tileBackground: RippleDrawable, v: View) {
        mRipple = tileBackground
        updateRippleSize(v)
    }

    //以view的中心为圆心，宽的1/4为半径的ripple范围
    private fun updateRippleSize(v: View) {
        // center the touch feedback on the center of the icon, and dial it down a bit
        if (v.width != 0) {
            val cx = v.width / 2
            val cy = v.height / 2
            val rad = (v.width * .5f).toInt()
            Log.d("ripple", "updateRippleSize: rad=$rad")
            mRipple!!.setHotspotBounds(cx - rad, cy - rad, cx + rad, cy + rad)
        } else {
            // TODO: 17-1-9
        }
    }

    //对外接口
    fun getRippleDrawable(context: Context, view: View): RippleDrawable? {
        mTileBackground = newTileBackground(context)
        if (mTileBackground is RippleDrawable) {
            setRipple(mTileBackground as RippleDrawable, view)
        }
        return mRipple
    }
}
