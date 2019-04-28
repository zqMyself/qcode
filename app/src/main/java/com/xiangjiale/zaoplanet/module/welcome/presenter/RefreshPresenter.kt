package com.xiangjiale.zaoplanet.module.welcome.presenter

import android.app.Application
import android.service.autofill.UserData
import com.frameworks.base.core.BaseEntry
import com.frameworks.base.di.scope.ActivityScope
import com.frameworks.base.http.ErrorHandleSubscriber
import com.frameworks.base.mvp.BasePresenter
import com.xiangjiale.zaoplanet.config.AppConfig
import com.xiangjiale.zaoplanet.module.welcome.bean.UserInfoBean
import com.xiangjiale.zaoplanet.module.welcome.contract.RefreshContract
import com.xiangjiale.zaoplanet.utils.RealmHelper
import com.xiangjiale.zaoplanet.utils.RequestUtils
import com.xiangjiale.zaoplanet.utils.UserDataUtils
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@ActivityScope
class RefreshPresenter @Inject
constructor(model: RefreshContract.Model, rootView: RefreshContract.View) : BasePresenter<RefreshContract.Model, RefreshContract.View>(model, rootView) {


    // 关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b
    fun requestRefresh(map: Map<String,Any>) {
        mModel!!.getRefresh(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe { _ ->
//                    mRootView.showProgressLoading()//显示下拉刷新的进度条
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
//                    mRootView.hideProgressLoading()//隐藏下拉刷新的进度条
                }
                .subscribe(object : ErrorHandleSubscriber<UserInfoBean>(){
                    override fun onSuccess(t: UserInfoBean) {
                        UserDataUtils.intance?.setUserData(t.data!!.user!!)
                        UserDataUtils.intance?.getUserData()!!.isOnlin          =  true //将当前用户设置为在线
                        UserDataUtils.intance?.getUserData()!!.accessToken      =  t.data!!.rememberMe //
                        UserDataUtils.intance?.getUserData()!!.time             =  t.data!!.time //
                        UserDataUtils.intance?.getUserData()!!.tokenType        =  t.data!!.tokenType //
                        UserDataUtils.intance?.getUserData()!!.configVersion    =  t.data!!.configVersion //
                        UserDataUtils.intance?.getUserData()!!.dataDicVersion   =  t.data!!.dataDicVersion //
                        //是否显示红包雨
                        AppConfig.isOpenRedPacketActivity                       =  t.data?.rewardConfigIsOn  ?:false
                        //登录次数
                        AppConfig.loginTimes                                    =  t.data?.loginTimes        ?:0
                        //将当前用户信息存入内存中
                        RequestUtils.setServertime(t.data!!.time.toLong())
                       //将当前用户信息添加到本地数据库中 第一次是添加，之后是更新
                        RealmHelper().addDog(UserDataUtils.intance!!.getUserData()!!)
                        //
                        mRootView!!.showSuccess(t.data!!)

                    }


                    override fun onException(code: Int, str: String) {
                        mRootView!!.showException(code,str)
                    }



                    override fun onEmpty(str: String) {
                    }
                })
    }

}