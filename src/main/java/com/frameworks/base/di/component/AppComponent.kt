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
package com.frameworks.base.di.component

import android.app.Application
import android.content.res.Resources
import com.frameworks.base.di.module.AppModule
import com.frameworks.base.di.module.ClientModule
import com.frameworks.base.interfaces.IRepositoryManager
import com.google.gson.Gson
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton

/**
 * <pre>
 * author   : zengqiang
 * e-mail   : 972022400@qq.com
 * time     : 2019/04/11
 * desc     :拿到此接口的实现类
 *           拥有此接口的实现类即可调用对应的方法拿到 Dagger 提供的对应实例
 * version  : 1.0
 * </pre>
 *
 */
@Singleton
@Component(modules = [AppModule::class, ClientModule::class])
interface AppComponent {
    fun application()       : Application
    fun gson()              : Gson
    fun resources()         : Resources
    fun retrofit()          : Retrofit
    fun repositoryManager() : IRepositoryManager
}
