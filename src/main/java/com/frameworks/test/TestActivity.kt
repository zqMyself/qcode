package com.frameworks.test


import android.app.Activity
import android.content.Intent
import com.frameworks.R
import com.frameworks.base.di.component.AppComponent
import com.frameworks.test.DaggerSplashPicIdComponent
import com.zqcode.base.framework.base.CodeBaseAcitivty
import kotlinx.android.synthetic.main.activity_main.*

class TestActivity : CodeBaseAcitivty<SplashPicIdPresenter>(), SplashPicIdContract.View {


    override val activity: Activity?
        get() = this
    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    override fun setupActivityComponent(appComponent: AppComponent) {


        DaggerSplashPicIdComponent.builder()
                .appComponent(appComponent)
                .splashPicIdModule(SplashPicIdModule(this))
                .build()
                .inject(this)

    }
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initView() {

        initTitleBar("测试")

        text1.setOnClickListener { startActivity(Intent(this, TestActivity2::class.java)) }


    }

    override fun initData() {


    }

    override fun onStart() {
        super.onStart()
        mPresenter?.requestSplashPicId(HashMap<String,Any>())

    }

}
