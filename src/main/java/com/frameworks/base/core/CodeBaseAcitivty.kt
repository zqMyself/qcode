package com.zqcode.base.framework.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.LinearLayout
import com.frameworks.R
import com.frameworks.base.interfaces.AppLifecycles
import com.frameworks.base.manager.AppManager
import com.frameworks.base.mvp.IPresenter
import com.frameworks.base.mvp.IView
import com.frameworks.base.widget.stateView.CodeStateView
import com.frameworks.base.utils.CodeAppUtils
import com.frameworks.base.widget.TitleBuilder
import com.zqcode.base.framework.base.interfaces.IActivity
import com.zqcode.base.framework.base.utils.CodeStatusBarCompat
import me.imid.swipebacklayout.lib.SwipeBackLayout
import me.imid.swipebacklayout.lib.Utils
import me.imid.swipebacklayout.lib.app.SwipeBackActivityBase
import me.imid.swipebacklayout.lib.app.SwipeBackActivityHelper
import me.yokeyword.fragmentation.SupportActivity
import me.yokeyword.fragmentation.anim.DefaultHorizontalAnimator
import me.yokeyword.fragmentation.anim.FragmentAnimator
import javax.inject.Inject
import kotlin.properties.Delegates


/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/11
 *     desc     :
 *     version  : 1.0
 * </pre>
 */
 @SuppressLint("RestrictedApi")
 abstract  class CodeBaseAcitivty<P : IPresenter> : SupportActivity(), SwipeBackActivityBase,IActivity, IView,CodeStateView.OnRetryClickListener,AppLifecycles {

    protected val TAG   = this.javaClass.simpleName

    //标题栏Toolbar
    public      var mCommonToolbar                   :Toolbar           ?= null
    //状态栏view
    protected   var mStatusBarView                   :View              ?= null
    //标题
    protected   var mTitleTextView                   :TitleBuilder      ?= null
    //上下文
    protected   var mContext                         :Context           ?= null

    //侧滑工具类
    private     var mHelper                          :SwipeBackActivityHelper by Delegates.notNull()

    //网络加载数据状态
    protected   var mStateView                       :CodeStateView     ?= null

    //跟布局view
    private     var mRootView                        :View              ?=null

    @Inject
    @JvmField
    internal    var mPresenter                       : P                ?= null//如果当前页面逻辑简单, Presenter 可以为 null


    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        setupActivityComponent(CodeAppUtils.mApplication!!.appComponent)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        mHelper     = SwipeBackActivityHelper(this)
        //创建侧滑类
        mHelper.onActivityCreate()
        super.onCreate(savedInstanceState)
        mRootView   = LinearLayout.inflate(this, getLayoutId(), null)

        setupActivityComponent(CodeAppUtils.mApplication!!.appComponent)
        AppManager.appManager!!.addActivity(this)

        //设置layout view
        setContentView(mRootView)
        //设置状态栏颜色
        setStatusBarColor(R.color.colorPrimaryDark)
        //初始化布局状态
        initStateView()
        //初始化view
        initView()
        //初始化数据
        initData()
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        mHelper.onPostCreate()
    }


    override  fun onCreateFragmentAnimator(): FragmentAnimator {
        return DefaultHorizontalAnimator()
                .setEnter(R.anim.translate_right_to_center)
                .setExit(R.anim.translate_center_to_left)
                .setPopEnter(R.anim.translate_right_to_center)
                .setPopExit(R.anim.translate_center_to_left)
    }

    override fun getSwipeBackLayout(): SwipeBackLayout {
        return mHelper.swipeBackLayout
    }

    override fun setSwipeBackEnable(enable: Boolean) {
        swipeBackLayout.setEnableGesture(enable)
    }

    override fun scrollToFinishActivity() {
        Utils.convertActivityToTranslucent(this)
        swipeBackLayout.scrollToFinishActivity()
    }

    override  fun attachBaseContext(base: Context){super.attachBaseContext(base)}

    override fun onCreate(application: Application){}

    override  fun onTerminate(application: Application){}

    /**
     * 设置状态栏颜色
     */
    protected fun setStatusBarColor(statusColor: Int) {
        mStatusBarView = CodeStatusBarCompat.compat(this, ContextCompat.getColor(this, statusColor))
    }

    /**
     * @param title 标题
     */
    protected fun initTitleBar(title: String): TitleBuilder {
        return TitleBuilder(this)
                .setTitleText(title)
                .setLeftOnClickListener(View.OnClickListener { onBackPressedSupport() })
    }

    /**
     * 左侧有返回键的标题栏 （自定义）
     *
     * 如果在此基础上还要加其他内容,比如右侧有文字按钮,可以获取该方法返回值继续设置其他内容
     *
     * @param title 标题
     */
    protected fun initBackTitle(title: String): TitleBuilder {
        return TitleBuilder(this)
                .setTitleText(title)
                .setLeftImage(R.mipmap.ic_back)
                .setLeftOnClickListener(View.OnClickListener { onBackPressedSupport() })
    }

    /**
     * 空界面
     */
    override fun showEmpty() {
    }

    /**
     * 没有更多数据了
     */
    override fun noMoreData() {
    }

    override fun showException(code: Int, message: String) {
    }

    /**
     * 显示加载页面
     */
    override fun showLoading() {
        mStateView!!.showLoading()
    }

    /**
     * 显示内容页面
     */
    override fun hideLoading() {
        mStateView!!.showContent()
    }

    /**
     * 显示错误页面
     */
    override fun showError() {
        mStateView!!.showRetry()
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