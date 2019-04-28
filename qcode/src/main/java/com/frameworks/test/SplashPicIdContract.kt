package com.frameworks.test

import android.app.Activity
import com.frameworks.base.core.BaseEntry
import com.frameworks.base.mvp.IModel
import com.frameworks.base.mvp.IView
import io.reactivex.Observable

interface SplashPicIdContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        val activity: Activity?
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getCollect(map:Map<String,Any>): Observable<BaseEntry<List<String>>>
    }
}