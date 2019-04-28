/**
 * Copyright 2016 JustWayward Team
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frameworks.base.utils

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.Activity
import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.graphics.Point
import android.graphics.Rect
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import android.view.ViewConfiguration
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import com.frameworks.CodeBaseApplication
import java.util.regex.Pattern
/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/11
 *     desc     :
 *     version  : 1.0
 * </pre>
 */
@SuppressLint("StaticFieldLeak")
object CodeAppUtils {

    var mAppContext                  : Context? = null
        private set
    var mApplication                 : CodeBaseApplication? = null
        
        private set
    //UI线程
    private var mUiThread            : Thread? = null
    //全局handler
    private val sHandler            = Handler(Looper.getMainLooper())

    fun init(context: CodeBaseApplication) {
        mAppContext = context
        mApplication = context
        mUiThread = Thread.currentThread()
    }

    /**
     * 字符
     */
    fun getString(ResId: Int): String {
        return mAppContext!!.resources.getString(ResId)
    }

    /**
     * 颜色
     */
    fun getColor(ResId: Int): Int {
        return mAppContext!!.resources.getColor(ResId)
    }

    /**
     * Drawable资源
     */
    fun getDrawable(ResId: Int): Drawable {
        return mAppContext!!.resources.getDrawable(ResId,null)
    }

    /**
     * 字符串数组
     */
    fun getStringArray(ResId: Int): Array<String> {
        return mAppContext!!.resources.getStringArray(ResId)
    }

    /**
     * dipתΪ px
     */
    fun dip2px(context: Context, dipValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (dipValue * scale + 0.5f).toInt()
    }

    /**
     * px תΪ dip
     */
    fun px2dip(context: Context, pxValue: Float): Int {
        val scale = context.resources.displayMetrics.density
        return (pxValue / scale + 0.5f).toInt()
    }


    /**dip转px */
    fun dipToPx(dip: Float): Int {
        //获取设备密度
        val density = mAppContext!!.resources.getDisplayMetrics().density
        return (dip * density + 0.5).toInt()
    }

    /**dip转px */
    fun testdipToPx(dip: Float): Float {
        //获取设备密度
        val density = mAppContext!!.resources.getDisplayMetrics().density
        return (dip * density).toFloat()
    }

    /**px转dip */
    fun pxToDip(px: Int): Float {
        val density = mAppContext!!.resources.getDisplayMetrics().density
        //px=设备密度*dp
        return px / density
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun px2sp(context: Context, pxVal: Float): Float {
        return pxVal / context.resources.displayMetrics.scaledDensity
    }

    /**
     * px转sp
     *
     * @param context
     * @param pxVal
     * @return
     */
    fun sp2px(context: Context, spVal: Float): Float {
        return spVal * context.resources.displayMetrics.scaledDensity
    }


    /**
     * 屏幕宽度
     * @param context
     * @return
     */
    @JvmStatic
    fun getSreenWidth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var point = Point()
        wm.defaultDisplay.getSize(point)
        return point.x
    }

    /**
     * 屏幕高度
     * @param context
     * @return
     */
    @JvmStatic
    fun getSreenHeigth(context: Context): Int {
        val wm = context
                .getSystemService(Context.WINDOW_SERVICE) as WindowManager
        var point = Point()
        wm.defaultDisplay.getSize(point)
        return point.y
    }


    /**
     * 显示软键盘
     * @param editText
     */
    fun showSoftInput(editText: EditText) {
        editText.isFocusable = true
        editText.isFocusableInTouchMode = true
        editText.requestFocus()
        val inputManager = editText.context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.showSoftInput(editText, 0)
    }

    /**
     * 隐藏软键盘
     * @param activity
     */
    fun hideSoftInput(activity: Context) {
        //获取当前屏幕内容的高度
        val screenHeight = (activity as Activity).window.decorView.height
        //获取View可见区域的bottom
        val rect = Rect()
        activity.window.decorView.getWindowVisibleDisplayFrame(rect)
        if (screenHeight - rect.bottom != 0) {
            val inputMethodManager = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 隐藏软键盘(只适用于Activity，不适用于Fragment)
     */
    fun hideSoftKeyboard(activity: Activity) {
        val view = activity.currentFocus
        if (view != null) {
            val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(view.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }

    /**
     * 隐藏软键盘(可用于Activity，Fragment)
     */
    fun hideSoftKeyboard(context: Context, viewList: List<View>?) {
        if (viewList == null) return

        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager

        for (v in viewList) {
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }


    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 关键字
     * @return
     */
    fun getHighLightKeyWord(color: Int, text: String, keyword: String): SpannableStringBuilder {
        val s = SpannableStringBuilder(text)
        val p = Pattern.compile(keyword)
        val m = p.matcher(s)
        while (m.find()) {
            val start = m.start()
            val end = m.end()
            s.setSpan(ForegroundColorSpan(color), start, end,
                    Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return s
    }

    /**
     * 只匹配第一个关键词
     */
    public fun matcherSearchText( color :Int, string :String, keyWord :String) : CharSequence{
        var builder =  SpannableStringBuilder(string)
        var indexOf = string.indexOf(keyWord)
        if (indexOf != -1) {
            builder.setSpan( ForegroundColorSpan(color), indexOf, indexOf + keyWord.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return builder;
    }
    /**
     * @param color 关键字颜色
     * @param text 文本
     * @param keyword 多个关键字
     * @return
     */
    fun getHighLightKeyWord(color: Int, text: String, keyword: List<String>): SpannableString {
        val s = SpannableString(text)
        for (i in keyword.indices) {
            val p = Pattern.compile(keyword[i])
            val m = p.matcher(s)
            while (m.find()) {
                val start = m.start()
                val end = m.end()
                s.setSpan(ForegroundColorSpan(color), start, end,
                        Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            }
        }
        return s

    }

    fun copyText(text: String) {
        //获取剪贴板管理器：
        val cm = mAppContext!!.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建普通字符型ClipData
        val mClipData = ClipData.newPlainText("Label", text)
        cm.primaryClip = mClipData
        CodeToastUtils.showToast(mAppContext!!, "复制成功")
    }


    fun toWebSite(context: Context, url: String) {
        val intent = Intent()
        intent.action = Intent.ACTION_VIEW
        intent.data = Uri.parse(url) // 注意此处的判断intent.resolveActivity()可以返回显示该Intent的Activity对应的组件名 // 官方解释 : Name of the component implementing an activity that can display the intent
        if (intent.resolveActivity(context.packageManager) != null) {
            context.startActivity(Intent.createChooser(intent, "请选择浏览器"))
        } else {
            CodeToastUtils.showToast(context, "没有匹配的程序")
        }
    }

    fun getStatusBarHeight(): Int {
        // 获得状态栏高度
        val resourceId = mAppContext!!.resources.getIdentifier("status_bar_height", "dimen", "android")
        return mAppContext!!.resources.getDimensionPixelSize(resourceId)
    }

    fun getNavigationBarHeight(): Int {
        var result = 0
        if (hasNavBar()) {
            val res = mAppContext!!.resources
            val resourceId = res.getIdentifier("navigation_bar_height", "dimen", "android")
            if (resourceId > 0) {
                result = res.getDimensionPixelSize(resourceId)
            }
        }
        return result
    }

    /**
     * 检查是否存在虚拟按键栏
     *
     * @param context
     * @return
     */
    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private fun hasNavBar(): Boolean {
        val res = mAppContext!!.resources
        val resourceId = res.getIdentifier("config_showNavigationBar", "bool", "android")
        if (resourceId != 0) {
            var hasNav = res.getBoolean(resourceId)
            // check override flag
            val sNavBarOverride = getNavBarOverride()
            if ("1" == sNavBarOverride) {
                hasNav = false
            } else if ("0" == sNavBarOverride) {
                hasNav = true
            }
            return hasNav
        } else { // fallback
            return !ViewConfiguration.get(mAppContext!!).hasPermanentMenuKey()
        }
    }

    /**
     * 判断虚拟按键栏是否重写
     *
     * @return
     */
    private fun getNavBarOverride():String {
        var sNavBarOverride = ""
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            try {
                val c = Class.forName("android.os.SystemProperties");
                val m = c.getDeclaredMethod("get", String::class.java)
                m.isAccessible = true
                sNavBarOverride =  m.invoke(null, "qemu.hw.mainkeys") as String
            } catch (e : Throwable) {

            }
        }
        return sNavBarOverride
    }

    fun getRealScreenWH(): Point {
        val windowManager = mAppContext!!.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val display = windowManager.defaultDisplay
        val outPoint = Point()
        if (Build.VERSION.SDK_INT >= 19) {
            // 可能有虚拟按键的情况
            display.getRealSize(outPoint)
        } else {
            // 不可能有虚拟按键
            display.getSize(outPoint)
        }
        return outPoint
    }


    fun getAppComponent() : Application {
        return mApplication!!
    }
}
