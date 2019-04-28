/*
 * Copyright 2017 JessYan
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.frameworks.base.mvp

import android.app.Activity

import com.frameworks.base.manager.EventBusManager

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     :基类 Presenter
 * version  : 1.0
</pre> *
 */

open class BasePresenter<M : IModel, V : IView> : IPresenter {

    protected val TAG = this.javaClass.simpleName

    protected var mCompositeDisposable  : CompositeDisposable   ? = null

    protected var mModel                : M                     ? = null
    protected var mRootView             : V                     ? = null

    /**
     * 如果当前页面同时需要 Model 层和 View 层,则使用此构造函数(默认)
     *
     * @param model
     * @param rootView
     */
    constructor(model: M, rootView: V) {
        this.mModel = model
        this.mRootView = rootView
        onStart()
    }

    /**
     * 如果当前页面不需要操作数据,只需要 View 层,则使用此构造函数
     *
     * @param rootView
     */
    constructor(rootView: V) {
        this.mRootView = rootView
        onStart()
    }

    constructor() {
        onStart()
    }

    override fun onStart() {
        //将 LifecycleObserver 注册给 LifecycleOwner 后 @OnLifecycleEvent 才可以正常使用
        if (useEventBus())
        //如果要使用 Eventbus 请将此方法返回 true
            EventBusManager.instance!!.register(this)//注册 Eventbus
    }

    /**
     * 在框架中 [Activity.onDestroy] 时会默认调用 [IPresenter.onDestroy]
     */
    override fun onDestroy() {
        if (useEventBus())
        //如果要使用 Eventbus 请将此方法返回 true
            EventBusManager.instance!!.unregister(this)//解除注册 Eventbus
        unDispose()//解除订阅
        if (mModel != null)
            mModel!!.onDestroy()
        this.mModel = null
        this.mRootView = null
        this.mCompositeDisposable = null
    }


    /**
     * 是否使用 EventBus
     *
     * @return 返回 `true` (默认为使用 `true`), Arms 会自动注册 EventBus
     */
    fun useEventBus(): Boolean {
        return true
    }


    /**
     * 将 [Disposable] 添加到 [CompositeDisposable] 中统一管理
     * 可在 [Activity.onDestroy] 中使用 [.unDispose] 停止正在执行的 RxJava 任务,避免内存泄漏
     *
     * @param disposable
     */
    fun addDispose(disposable: Disposable) {
        if (mCompositeDisposable == null) {
            mCompositeDisposable = CompositeDisposable()
        }
        //将所有 Disposable 放入集中处理
        mCompositeDisposable!!.add(disposable)
    }

    /**
     * 停止集合中正在执行的 RxJava 任务
     */
    fun unDispose() {
        if (mCompositeDisposable != null) {
            //保证 Activity 结束时取消所有正在执行的订阅
            mCompositeDisposable!!.clear()
        }
    }


}
