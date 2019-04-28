package com.xiangjiale.zaoplanet

import com.frameworks.CodeBaseApplication
import io.realm.Realm
import io.realm.RealmConfiguration

open class XjlApplication : CodeBaseApplication() {


    override fun onCreate() {
        super.onCreate()

//        Realm.init(this)
//        val config = RealmConfiguration.Builder().build()
//        Realm.setDefaultConfiguration(config)
    }

    override fun getUrls(): String {
        return BuildConfig.BASE_URL
    }
}