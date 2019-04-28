package com.frameworks.test

import com.frameworks.base.di.scope.ActivityScope
import dagger.Module
import dagger.Provides

/**
 * Created by zengqiang on 2018/10/9
 * 从入门到放弃
 */
@Module
class SplashPicIdModule
/**
 * 构建 SplashPicIdModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
 * @param view
 */
(private val view: SplashPicIdContract.View) {
    @ActivityScope
    @Provides
    internal fun provideSplashPicIdView(): SplashPicIdContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideSplashPicIdModel(model: SplashPicIdModel): SplashPicIdContract.Model {
        return model
    }

}
