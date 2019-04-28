package com.xiangjiale.zaoplanet.module.welcome.model

import android.arch.lifecycle.Lifecycle
import android.arch.lifecycle.OnLifecycleEvent
import com.frameworks.base.di.scope.ActivityScope
import com.frameworks.base.interfaces.IRepositoryManager
import com.frameworks.base.mvp.BaseModel
import com.xiangjiale.zaoplanet.config.ApiService
import com.xiangjiale.zaoplanet.module.welcome.bean.UserInfoBean
import com.xiangjiale.zaoplanet.module.welcome.contract.RefreshContract

import io.reactivex.Observable
import javax.inject.Inject


/**
 * Created by zengqiang on 2018/9/29
 * 从入门到放弃
 */
@ActivityScope
class RefreshModel @Inject
constructor(repositoryManager: IRepositoryManager) : BaseModel(repositoryManager), RefreshContract.Model {

    override fun getRefresh(map: Map<String, Any>): Observable<UserInfoBean> {
        return mRepositoryManager!!.obtainRetrofitService(ApiService::class.java).getRefreshLogin(map)
    }

}
