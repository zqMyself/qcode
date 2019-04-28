package com.xiangjiale.zaoplanet.plugin

import android.app.Activity
import android.content.res.Resources
import android.os.Bundle

import com.frameworks.base.interfaces.AppLifecycles

import java.lang.reflect.InvocationTargetException


class ProxyActivity : Activity() {
    /**
     * 要跳转的activity的name
     */
    private var className = ""
    private var appInterface: AppLifecycles? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        /**
         * step1：得到插件app的activity的className
         */
        className = intent.getStringExtra("className")


        /**
         * step2：通过反射拿到class，
         * 但不能用以下方式
         * classLoader.loadClass(className)
         * Class.forName(className)
         * 因为插件app没有被安装！
         * 这里我们调用我们重写过多classLoader
         */
        try {
            val activityClass   = classLoader.loadClass(className)
            val constructor     = activityClass.getConstructor()
            val instance        = constructor.newInstance()
            appInterface        = instance as AppLifecycles

            appInterface!!.attachBaseContext(this)
//            val bundle = Bundle()
//            appInterface!!.onCreate(bundle)
        } catch (e: ClassNotFoundException) {
            e.printStackTrace()
        } catch (e: NoSuchMethodException) {
            e.printStackTrace()
        } catch (e: InstantiationException) {
            e.printStackTrace()
        } catch (e: IllegalAccessException) {
            e.printStackTrace()
        } catch (e: InvocationTargetException) {
            e.printStackTrace()
        }

    }

    override fun onStart() {
        super.onStart()
//        appInterface!!.onStart()
    }


    override fun onResume() {
        super.onResume()
//        appInterface!!.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
//        appInterface!!.onDestroy()
    }

    override fun getClassLoader(): ClassLoader {
        //不用系统的ClassLoader，用dexClassLoader加载
        return PluginManager.instance.dexClassLoader!!
    }

    override fun getResources(): Resources {
        //不用系统的resources，自己实现一个resources
        return PluginManager.instance.resources!!
    }

}
