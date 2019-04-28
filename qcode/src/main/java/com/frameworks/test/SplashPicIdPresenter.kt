package com.frameworks.test

import com.frameworks.base.core.BaseEntry
import com.frameworks.base.http.ErrorHandleSubscriber
import com.frameworks.base.mvp.BasePresenter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by zengqiang on 2018/9/29
 * 从入门到放弃
 */
class SplashPicIdPresenter @Inject
constructor(model: SplashPicIdContract.Model,rootView: SplashPicIdContract.View) : BasePresenter<SplashPicIdContract.Model, SplashPicIdContract.View>(model, rootView) {

    // 关于RxCache缓存库的使用请参考 http://www.jianshu.com/p/b58ef6b0624b
    fun requestSplashPicId(map: Map<String,Any>) {
        mModel!!.getCollect(map)
                .subscribeOn(Schedulers.io())
                .doOnSubscribe {
                }.subscribeOn(AndroidSchedulers.mainThread())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {

                }.subscribe(object : ErrorHandleSubscriber<BaseEntry<List<String>>>() {
                    override fun onSuccess(t: BaseEntry<List<String>>) {
                    }

                    override fun onEmpty(str: String) {
                    }

                    override fun onException(code: Int, str: String) {
                    }

                })

    }


    override fun onDestroy() {
        super.onDestroy()
    }
}


