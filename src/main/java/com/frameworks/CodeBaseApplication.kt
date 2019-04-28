package com.frameworks

/**
 * <pre>
 *     author   : zengqiang
 *     e-mail   : 972022400@qq.com
 *     time     : 2019/04/11
 *     desc     :
 *     version  : 1.0
 * </pre>
 */

import android.app.Application
import com.frameworks.base.core.CommonData
import com.frameworks.base.di.component.AppComponent
import com.frameworks.base.di.component.DaggerAppComponent
import com.frameworks.base.di.module.AppModule
import com.frameworks.base.interfaces.App
import com.frameworks.base.utils.CodeAppUtils


 open abstract class CodeBaseApplication :Application(),App {

    var mAppComponent : AppComponent      ?=null
    var mHostUrls                          = ""

    override fun onCreate() {
        super.onCreate()
        CodeAppUtils.init(this)
        initComponent()
    }

    override val appComponent: AppComponent
        get() {
            return mAppComponent!!
        }



    /**
     * 公共参数
     */
    override val commonData: CommonData
        get() {
           var commonData       =  CommonData()
            commonData.koten    = "xjl2018"
            commonData.sign     = "xjl2018"
            commonData.version  = BuildConfig.VERSION_NAME
            return  commonData
        }

     private fun initComponent() {
         mAppComponent           = DaggerAppComponent
                 .builder()
                 .appModule(AppModule(this))
                 .build()
     }

     abstract  fun getUrls(): String
}