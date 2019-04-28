package com.frameworks.test


import android.annotation.SuppressLint
import android.view.View
import com.frameworks.R
import com.frameworks.base.di.component.AppComponent
import com.frameworks.base.mvp.BasePresenter
import com.zqcode.base.framework.base.CodeBaseAcitivty

class TestActivity2 : CodeBaseAcitivty<BasePresenter<*,*>>() {
    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    override fun setupActivityComponent(appComponent: AppComponent) {


    }


    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }


    override fun initView() {
        initTitleBar("测试2")
    }

    override fun initData() {
        showLoading()
    }

    @SuppressLint("WrongViewCast")
    override fun injectTarget(): View {
        return findViewById<View>(R.id.text1)
    }






}
