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

import com.frameworks.base.interfaces.IRepositoryManager
import kotlin.properties.Delegates


/**
 * ================================================
 * 基类 Model
 *
 * @see [Model wiki 官方文档](https://github.com/JessYanCoding/MVPArms/wiki.2.4.3)
 * Created by JessYan on 08/05/2016 12:55
 * [Contact me](mailto:jess.yan.effort@gmail.com)
 * [Follow me](https://github.com/JessYanCoding)
 * ================================================
 */
open class BaseModel : IModel {

    var mRepositoryManager : IRepositoryManager ?= null
    constructor( iRepositoryManager : IRepositoryManager){
        this.mRepositoryManager  = iRepositoryManager
    }
    //用于管理网络请求层, 以及数据缓存层
    override fun onDestroy() {
        mRepositoryManager = null
    }
}
