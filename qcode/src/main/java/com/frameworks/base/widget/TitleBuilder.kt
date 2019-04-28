package com.frameworks.base.widget

import android.app.Activity
import android.content.Context
import android.text.TextUtils
import android.view.View
import android.view.View.OnClickListener
import android.widget.ImageView
import android.widget.TextView

import com.frameworks.R
import com.frameworks.base.utils.CodeRippleUtils

/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     : 标题栏构造器,使用方法 new TitleBuilder().setMethod().setMethod()......
 *            统一格式为标题文字,左右各自是文字/图片按钮
 *            按钮都默认不显示,只有在你调用setLeftText时才会显示左侧按钮文字,图片同理
 *            图片或文字的点击事件都用Left/RightOnClickListener
 * version  : 1.0
 *</pre>
 *
 */
class TitleBuilder {

    private var mContext: Context? = null
    var rootView: View? = null
        private set
    var tvTitle: TextView? = null
        private set
    private var ivMiddle: ImageView? = null
    var ivLeft: ImageView? = null
        private set
    var ivRight: ImageView? = null
        private set
    var tvLeft: TextView? = null
        private set
    var tvRight: TextView? = null
        private set


    /**
     * Activity中使用这个构造方法
     */
    constructor(context: Activity) {
        this.mContext = context
        rootView = context.findViewById(R.id.rl_titlebar)
        if (rootView == null) {
            return
        }

        tvTitle = rootView!!.findViewById<View>(R.id.titlebar_tv) as TextView
        ivMiddle = rootView!!.findViewById<View>(R.id.titlebar_iv_middle) as ImageView
        ivLeft = rootView!!.findViewById<View>(R.id.titlebar_iv_left) as ImageView
        ivRight = rootView!!.findViewById<View>(R.id.titlebar_iv_right) as ImageView
        tvLeft = rootView!!.findViewById<View>(R.id.titlebar_tv_left) as TextView
        tvRight = rootView!!.findViewById<View>(R.id.titlebar_tv_right) as TextView

        //设置水波点击效果
        ivLeft!!.background = CodeRippleUtils.getRippleDrawable(mContext!!, ivLeft!!)
        ivRight!!.background = CodeRippleUtils.getRippleDrawable(mContext!!, ivRight!!)
    }

    /**
     * Fragment中使用这个构造方法
     */
    constructor(context: Context, view: View) {
        this.mContext = context
        rootView = view.findViewById(R.id.rl_titlebar)
        if (rootView == null) {
            return
        }
        tvTitle = rootView!!.findViewById<View>(R.id.titlebar_tv) as TextView
        ivMiddle = rootView!!.findViewById<View>(R.id.titlebar_iv_middle) as ImageView
        ivLeft = rootView!!.findViewById<View>(R.id.titlebar_iv_left) as ImageView
        ivRight = rootView!!.findViewById<View>(R.id.titlebar_iv_right) as ImageView
        tvLeft = rootView!!.findViewById<View>(R.id.titlebar_tv_left) as TextView
        tvRight = rootView!!.findViewById<View>(R.id.titlebar_tv_right) as TextView
    }

    // title
    fun setTitleBgRes(resid: Int): TitleBuilder {
        rootView!!.setBackgroundResource(resid)
        return this
    }

    fun setTitleText(text: String): TitleBuilder {
        tvTitle!!.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
        tvTitle!!.text = text
        return this
    }

    // left
    fun setLeftImage(resId: Int): TitleBuilder {
        ivLeft!!.visibility = if (resId > 0) View.VISIBLE else View.GONE
        ivLeft!!.setImageResource(resId)
        return this
    }

    //中间圆形图片
    fun setCircularMiddleImage(url: String): TitleBuilder {
        if (TextUtils.isEmpty(url)) {
            ivMiddle!!.visibility = View.GONE
        } else {
            ivMiddle!!.visibility = View.VISIBLE
        }
        return this
    }

    //左边原画图片
    fun setCircularLeftImage(url: String): TitleBuilder {
        if (TextUtils.isEmpty(url)) {
            ivLeft!!.visibility = View.GONE
        } else {
            ivLeft!!.visibility = View.VISIBLE
        }
        return this
    }

    fun setLeftText(text: String): TitleBuilder {
        tvLeft!!.visibility = if (TextUtils.isEmpty(text)) View.GONE else View.VISIBLE
        tvLeft!!.text = text
        return this
    }

    fun setLeftTextSize(dimes: Float): TitleBuilder {
        tvLeft!!.textSize = dimes
        return this
    }

    fun setLeftOnClickListener(listener: OnClickListener): TitleBuilder {
        if (ivLeft!!.visibility == View.VISIBLE) {
            ivLeft!!.setOnClickListener(listener)
        } else if (tvLeft!!.visibility == View.VISIBLE) {
            tvLeft!!.setOnClickListener(listener)
        }
        return this
    }

    // right
    fun setRightImage(resId: Int): TitleBuilder {
        ivRight!!.visibility = if (resId > 0) View.VISIBLE else View.GONE
        ivRight!!.setImageResource(resId)
        return this
    }

    //左边原画图片
    fun setCircularRightImage(url: String): TitleBuilder {
        if (TextUtils.isEmpty(url)) {
            ivRight!!.visibility = View.GONE
        } else {
            ivRight!!.visibility = View.VISIBLE
        }
        return this
    }

    fun setRightText(text: String): TitleBuilder {
        tvRight!!.visibility = if (TextUtils.isEmpty(text))
            View.GONE
        else
            View.VISIBLE
        tvRight!!.text = text
        return this
    }

    fun setRightTextColor(context: Context, resId: Int): TitleBuilder {
        tvRight!!.setTextColor(context.resources.getColor(resId))
        return this
    }

    fun setLeftTextColor(context: Context, resId: Int): TitleBuilder {
        tvLeft!!.setTextColor(context.resources.getColor(resId))
        return this
    }

    fun setTitleTextColor(context: Context, resId: Int): TitleBuilder {
        tvTitle!!.setTextColor(context.resources.getColor(resId))
        return this
    }


    fun setRightOnClickListener(listener: OnClickListener): TitleBuilder {
        if (ivRight!!.visibility == View.VISIBLE) {
            ivRight!!.setOnClickListener(listener)
        } else if (tvRight!!.visibility == View.VISIBLE) {
            tvRight!!.setOnClickListener(listener)
        }
        return this
    }

    fun setRightImageOnClickListener(listener: OnClickListener): TitleBuilder {
        ivRight!!.visibility = View.VISIBLE
        ivRight!!.setOnClickListener(listener)
        return this
    }

    fun setRightTextImage(left: Int, top: Int, right: Int, bottom: Int): TitleBuilder {
        tvRight!!.visibility = View.VISIBLE
        tvRight!!.setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom)
        return this
    }

    fun setRightTextOnClickListener(listener: OnClickListener): TitleBuilder {
        tvRight!!.visibility = View.VISIBLE
        tvRight!!.setOnClickListener(listener)
        return this
    }

    fun build(): View? {
        return rootView
    }

}
