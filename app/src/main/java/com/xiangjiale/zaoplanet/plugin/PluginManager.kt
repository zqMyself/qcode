package com.xiangjiale.zaoplanet.plugin

import android.content.Context
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import android.util.Log

import java.io.FileOutputStream

import dalvik.system.DexClassLoader


class PluginManager {
    //-------1:构建单例类end--------
    private var context : Context                   ?= null

    //-------2:获取插件app入口activity name start--------
    //得到packageManager来获取包信息
    //参数一是apk的路径，参数二是希望得到的内容
    //得到插件app的入口activity名称
    var         entryName       : String            ?= null
        private set(path) {
            val packageManager  = context!!.packageManager
            val packageInfo     = packageManager.getPackageArchiveInfo(path, PackageManager.GET_ACTIVITIES)
                    ?: return
            field               = packageInfo.activities[0].name
        }

    //-------3:构造classLoader
    var dexClassLoader          : DexClassLoader    ?= null
        private set

    //-------4:构造resources
    var resources               : Resources         ?= null
        private set

    fun setContext(context: Context) {
        this.context = context.applicationContext
    }

    fun loadPath(path: String) {
        entryName    = path
        setClassLoader(path)
        setResources(path)
    }


    fun setClassLoader(context: Context, fileName: String, filePath: String) {
        Thread(Runnable {
            try {
                val stream  = context.assets.open(fileName)
                val os      = FileOutputStream(filePath)
                val buffer  = ByteArray(1024)
                var len     = stream.read(buffer)

                while ( len > 0) {
                    os.write(buffer, 0, len)
                    len     = stream.read(buffer)
                }
                os.close()
                stream.close()
                Log.e("tag", "dexOutFile")
                loadPath(filePath)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("tag", "dexOutFile Exception")
            }
        }).start()


    }


    private fun setClassLoader(path: String) {

        //dex的缓存路径
        val dexOutFile        = context!!.getDir("dex", Context.MODE_PRIVATE)

        if (dexClassLoader == null) {
            dexClassLoader    = DexClassLoader(path, dexOutFile.absoluteFile.absolutePath, null, context!!.classLoader)
        }
    }

    fun setResources(path: String) {
        //由于构建resources必须要传入AssetManager，这里先构建一个AssetManager
        try {
            val assetManager = AssetManager::class.java.newInstance()
            val addAssetPath = AssetManager::class.java.getMethod("addAssetPath", String::class.java)
            addAssetPath.invoke(assetManager, path)

            resources        = Resources(assetManager, context!!.resources.displayMetrics, context!!.resources.configuration)
        } catch (e: Exception) {
        }

    }

    //-------4:构造resources end--------
    fun clearAssertionStatus() {
        dexClassLoader!!.clearAssertionStatus()

    }

    companion object {
        //-------1:构建单例类start--------
        val instance        = PluginManager()
    }
}
