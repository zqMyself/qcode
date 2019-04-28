package com.frameworks.test

import com.frameworks.base.di.component.AppComponent
import com.frameworks.base.di.scope.ActivityScope
import dagger.Component
@ActivityScope
@Component(modules = [SplashPicIdModule::class],dependencies = [AppComponent::class])
interface SplashPicIdComponent {
    fun inject(activity: TestActivity)
}