package com.frameworks.test

import com.frameworks.base.core.BaseEntry
import com.frameworks.base.interfaces.IRepositoryManager
import com.frameworks.base.mvp.BaseModel
import io.reactivex.Observable
import javax.inject.Inject

/**
 * Created by zengqiang on 2018/9/29
 * 从入门到放弃
 */
class SplashPicIdModel @Inject constructor(var repositoryManager: IRepositoryManager): BaseModel(repositoryManager), SplashPicIdContract.Model {

    override fun getCollect(map: Map<String, Any>): Observable<BaseEntry<List<String>>> {
        return  repositoryManager.obtainRetrofitService(CodeApiService::class.java).getSplashPicId(map)
    }

}
