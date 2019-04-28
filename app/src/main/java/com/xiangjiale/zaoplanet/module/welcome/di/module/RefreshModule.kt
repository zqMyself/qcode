package com.xiangjiale.zaoplanet.module.welcome.di.module

import com.frameworks.base.di.scope.ActivityScope
import com.xiangjiale.zaoplanet.module.welcome.contract.RefreshContract
import com.xiangjiale.zaoplanet.module.welcome.model.RefreshModel
import dagger.Module
import dagger.Provides

/**
 * Created by zengqiang on 2018/10/9
 * 从入门到放弃
 */
@Module
class RefreshModule
/**
 * 构建 RefreshModule 时,将 View 的实现类传进来,这样就可以提供 View 的实现类给 Presenter
 * @param view
 */
(private val view: RefreshContract.View) {

    @ActivityScope
    @Provides
    internal fun provideRefreshView(): RefreshContract.View {
        return this.view
    }

    @ActivityScope
    @Provides
    internal fun provideRefreshModel(model: RefreshModel): RefreshContract.Model {
        return model
    }
}
