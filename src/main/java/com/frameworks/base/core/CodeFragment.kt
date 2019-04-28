package com.frameworks.base.core

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.frameworks.base.mvp.IPresenter
import com.frameworks.base.widget.stateView.CodeStateView
import com.frameworks.base.widget.TitleBuilder
import com.zqcode.base.framework.base.interfaces.IFragment
import me.yokeyword.fragmentation_swipeback.SwipeBackFragment
import javax.inject.Inject

/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/16
 *     desc     :
 *     version  : 1.0
 * </pre>
 */
abstract  class CodeFragment<P : IPresenter> : SwipeBackFragment() , IFragment, CodeStateView.OnRetryClickListener {


    protected   val TAG = this.javaClass.simpleName
    //跟布局
    open        var mRootView                        :View             ?= null
    //状态栏view
    protected   var mStatusBarView                   :View             ?= null
    //标题
    protected   var mTitleTextView                   :TitleBuilder     ?= null
    //上下文
    protected   var mContext                         :Context          ?= null
    //布局状态View
    open        var mStateView                       :CodeStateView    ?= null

    @Inject
    @JvmField
    internal    var mPresenter                       : P                ?= null//如果当前页面逻辑简单, Presenter 可以为 null


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        if (mRootView == null) {
            mRootView = View.inflate(mContext, getLayoutId(), null)
        }
        initStateView()
        return attachToSwipeBack(mRootView)
    }

    override fun onRetryClick() {

    }

    override fun onRetryClick(stateViewType: CodeStateView.StateViewType?) {

    }

    /**
     * 初始化布局状态view
     */
    private fun initStateView() {
        mStateView = CodeStateView.inject(injectTarget())
        mStateView?.setOnRetryClickListener(this)
    }

    /**
     * 状态布局在的根布局 可根据子类来实现
     */
    open fun injectTarget(): View {
        return mRootView!!
    }

}