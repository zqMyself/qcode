package com.xiangjiale.zaoplanet.module.welcome.activity

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.frameworks.base.utils.CodeAppUtils
import com.frameworks.base.utils.CodeLogUtils
import com.frameworks.test.SplashPicIdContract
import com.xiangjiale.zaoplanet.R
import com.xiangjiale.zaoplanet.module.welcome.bean.UserData
import com.xiangjiale.zaoplanet.module.welcome.contract.RefreshContract
import com.xiangjiale.zaoplanet.module.welcome.di.component.DaggerRefreshComponent
import com.xiangjiale.zaoplanet.module.welcome.di.module.RefreshModule
import com.xiangjiale.zaoplanet.module.welcome.presenter.RefreshPresenter
import javax.inject.Inject

class WelcomeActivity : AppCompatActivity(), RefreshContract.View {


    @Inject
    @JvmField
    internal var mRefreshPresenter : RefreshPresenter    ?= null

    init {


        DaggerRefreshComponent.builder()
                .appComponent(CodeAppUtils.mApplication!!.appComponent)
                .refreshModule(RefreshModule(this))
                .build()
                .inject(this)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (mRefreshPresenter !=null) {
            mRefreshPresenter?.requestRefresh(HashMap())
        }else {
            CodeLogUtils.debugInfo("kong")
        }
    }
    /**
     * 错误界面
     */
    override fun showError() {

    }

    /**
     * 空界面
     */
    override fun showEmpty() {
    }

    /**
     * 没有更多数据了
     */
    override fun noMoreData() {
    }

    override fun showException(code: Int, message: String) {
    }

    override fun showSuccess(data: UserData) {
    }


}