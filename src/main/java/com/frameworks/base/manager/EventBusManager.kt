/*
 * Copyright 2018 JessYan
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
package com.frameworks.base.manager

import org.simple.eventbus.EventBus

/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/11
 *     desc     :
 *     version  : 1.0
 * </pre>
 */
class EventBusManager private constructor() {

    /**
     * 注册订阅者, 允许在项目中同时依赖两个 EventBus, 只要您喜欢
     *
     * @param subscriber 订阅者
     */
    fun register(subscriber: Any) {
        EventBus.getDefault().register(subscriber)
    }

    fun registerSticky(subscriber: Any) {
        EventBus.getDefault().registerSticky(subscriber)
    }

    fun unRegisterSticky(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    /**
     * 注销订阅者, 允许在项目中同时依赖两个 EventBus, 只要您喜欢
     *
     * @param subscriber 订阅者
     */
    fun unregister(subscriber: Any) {
        EventBus.getDefault().unregister(subscriber)
    }

    /**
     * 发送事件, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 发送事件
     *
     * @param event 事件
     */
    fun post(event: Any) {
        EventBus.getDefault().post(event)
    }

    fun post(event: Any, tag: String) {
        EventBus.getDefault().post(event, tag)
    }

    /**
     * 发送黏性事件, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 发送黏性事件
     *
     * @param event 事件
     */
    fun postSticky(event: Any) {
        EventBus.getDefault().postSticky(event)
    }

    fun postSticky(event: Any, tag: String) {
        EventBus.getDefault().postSticky(event)
    }

    /**
     * 注销黏性事件, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 注销黏性事件
     *
     * @param eventType
     * @return
     */
    fun removeStickyEvent(eventType: Class<*>) {
        EventBus.getDefault().removeStickyEvent(eventType)
    }

    /**
     * 清除订阅者和事件的缓存, 如果您在项目中同时依赖了两个 EventBus, 请自己使用想使用的 EventBus 的 Api 清除订阅者和事件的缓存
     */
    fun clear() {
        EventBus.getDefault().clear()
    }


    private fun isSystemCalss(name: String): Boolean {
        return name.startsWith("java.") || name.startsWith("javax.") || name.startsWith("android.")
    }

    companion object {
        @Volatile
        private var sInstance: EventBusManager? = null

        val instance: EventBusManager?
            get() {
                if (sInstance == null) {
                    synchronized(EventBusManager::class.java) {
                        if (sInstance == null) {
                            sInstance = EventBusManager()
                        }
                    }
                }
                return sInstance
            }
    }
}
