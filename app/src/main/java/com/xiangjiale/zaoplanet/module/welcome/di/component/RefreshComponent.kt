package com.xiangjiale.zaoplanet.module.welcome.di.component

import com.frameworks.base.di.component.AppComponent
import com.frameworks.base.di.scope.ActivityScope
import com.xiangjiale.zaoplanet.module.welcome.activity.WelcomeActivity
import com.xiangjiale.zaoplanet.module.welcome.di.module.RefreshModule
import dagger.Component

/**
 * Created by zengqiang on 2018/9/29
 * 从入门到放弃
 */
@ActivityScope
@Component(modules = [(RefreshModule::class)], dependencies = [(AppComponent::class)])
interface RefreshComponent {
    fun inject(activity: WelcomeActivity)
}