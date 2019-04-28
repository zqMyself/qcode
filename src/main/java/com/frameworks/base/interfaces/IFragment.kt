package com.zqcode.base.framework.base.interfaces

import com.frameworks.base.di.component.AppComponent


/**
 * <pre>
 *     author           : zengqiang
 *     e-mail           : 972022400@qq.com
 *     time             : 2019/04/11
 *     assistant name   ：让多年后的自己看得起自己
 *     desc             :Fragment中的抽象方法
 *     version          : 1.0
 * </pre>
 */
interface IFragment {
    /**
     * @return 获取layout布局
     */
    fun getLayoutId() : Int

    /**
     * 初始化view
     */
    fun initView()

    /**
     * 初始化数据
     */
    fun initData()

    /**
     * 提供 AppComponent (提供所有的单例对象) 给实现类, 进行 Component 依赖
     *
     * @param appComponent
     */
    fun setupActivityComponent(appComponent: AppComponent)

}