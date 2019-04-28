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
package com.jess.arms.integration

import com.frameworks.base.interfaces.IRepositoryManager
import com.frameworks.base.utils.CodeLogUtils
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import java.lang.reflect.InvocationHandler
import java.lang.reflect.Method
import java.lang.reflect.Proxy
import javax.inject.Inject
import javax.inject.Singleton

/**
 * ================================================
 * 用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 * 提供给 [IModel] 层必要的 Api 做数据处理
 *
 * @see [RepositoryManager wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.2.3)
 * Created by JessYan on 13/04/2017 09:52
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */

@Singleton
class RepositoryManager  : IRepositoryManager {

    @Inject
    @JvmField
    internal var mRetrofit: Retrofit ?= null

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass CodeApiService class
     * @param <T>          CodeApiService class
     * @return CodeApiService
    </T> */
    @Synchronized
    override fun <T : Any> obtainRetrofitService(serviceClass: Class<T>): T {
        return createWrapperService(serviceClass)
    }

    /**
     * 根据 https://zhuanlan.zhihu.com/p/40097338 对 Retrofit 进行的优化
     *
     * @param serviceClass CodeApiService class
     * @param <T>          CodeApiService class
     * @return CodeApiService
    </T> */
    private fun <T : Any> createWrapperService(serviceClass: Class<T>): T {
        // 通过二次代理，对 Retrofit 代理方法的调用包进新的 Observable 里在 io 线程执行。
        return Proxy.newProxyInstance(serviceClass.classLoader,
                arrayOf<Class<*>>(serviceClass), InvocationHandler { proxy, method, args ->
            if (method.returnType == Observable::class.java) {
                // 如果方法返回值是 Observable 的话，则包一层再返回
                return@InvocationHandler Observable.defer {
                    val service = getRetrofitService(serviceClass)
                    // 执行真正的 Retrofit 动态代理的方法
                    (getRetrofitMethod(service, method)
                            .invoke(service, *args) as Observable<*>)
                            .subscribeOn(Schedulers.io())
                }.subscribeOn(Schedulers.single())
            }
            // 返回值不是 Observable 的话不处理
            val service = getRetrofitService(serviceClass)
            getRetrofitMethod(service, method).invoke(service, *args)
        }) as T
    }

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param serviceClass CodeApiService class
     * @param <T>          CodeApiService class
     * @return CodeApiService
    </T> */
    private fun <T> getRetrofitService(serviceClass: Class<T>): T {

        if (mRetrofit == null) {
            CodeLogUtils.debugInfo("CodeApiService")
        }
        var retrofitService = mRetrofit!!.create(serviceClass)

        return retrofitService
    }

    @Throws(NoSuchMethodException::class)
    private fun <T : Any> getRetrofitMethod(service: T, method: Method): Method {
        return service.javaClass.getMethod(method.name, *method.parameterTypes)
    }


}
