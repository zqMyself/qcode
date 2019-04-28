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
package com.frameworks.base.interfaces;

/**
 * <pre>
 *     author           : zengqiang
 *     e-mail           : 972022400@qq.com
 *     time             : 2019/04/11
 *     assistant name   ：让多年后的自己看得起自己
 *     desc             :用来管理网络请求层,以及数据缓存层,以后可能添加数据库请求层
 *                       提供给 {@link com.frameworks.base.mvp.IModel} 必要的 Api 做数据处理
 *     version          : 1.0
 * </pre>
 */
public interface IRepositoryManager {

    /**
     * 根据传入的 Class 获取对应的 Retrofit service
     *
     * @param service
     * @param <T>
     * @return
     */
    <T> T obtainRetrofitService(Class<T> service);

}
