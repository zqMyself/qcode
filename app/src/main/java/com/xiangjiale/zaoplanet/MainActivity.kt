package com.xiangjiale.zaoplanet

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.frameworks.base.di.component.AppComponent
import com.frameworks.base.mvp.BasePresenter
import com.zqcode.base.framework.base.CodeBaseAcitivty

class MainActivity : CodeBaseAcitivty<BasePresenter<*,*>>() {
    /**
     * @return 获取layout布局
     */
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    /**
     * 初始化view
     */
    override fun initView() {
    }

    /**
     * 初始化数据
     */
    override fun initData() {
    }

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    override fun setupActivityComponent(appComponent: AppComponent) {

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }
}
