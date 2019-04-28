package com.xiangjiale.zaoplanet.module.welcome.contract

import android.app.Activity
import com.frameworks.base.mvp.IModel
import com.frameworks.base.mvp.IView
import com.xiangjiale.zaoplanet.module.welcome.bean.UserData
import com.xiangjiale.zaoplanet.module.welcome.bean.UserInfoBean

import io.reactivex.Observable

/**
 * Created by zengqiang on 2018/9/29
 * 从入门到放弃
 */
interface RefreshContract {
    //对于经常使用的关于UI的方法可以定义到IView中,如显示隐藏进度条,和显示文字消息
    interface View : IView {
        fun showSuccess(data: UserData)
    }

    //Model层定义接口,外部只需关心Model返回的数据,无需关心内部细节,如是否使用缓存
    interface Model : IModel {
        fun getRefresh(map: Map<String,Any>): Observable<UserInfoBean>
    }
}