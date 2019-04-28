package com.xiangjiale.zaoplanet.utils


import com.xiangjiale.zaoplanet.module.welcome.bean.ProfileBean
import com.xiangjiale.zaoplanet.module.welcome.bean.UserBean

import io.realm.Realm
import io.realm.RealmResults

class RealmHelper {
    val realm: Realm?

    init {
        realm = Realm.getDefaultInstance()
    }

    /**
     * add （增）
     */
    fun addDog(userInfoBean: UserBean) {
        realm!!.beginTransaction()
        realm.copyToRealmOrUpdate(userInfoBean)
        realm.commitTransaction()
    }

    fun addProfileBean(userInfoBean: ProfileBean) {
        realm!!.beginTransaction()
        realm.copyToRealmOrUpdate(userInfoBean)
        realm.commitTransaction()
    }

    /**
     * delete （删）
     */
    fun deleteDog(id: String) {
        val dog = realm!!.where(UserBean::class.java).equalTo("id", id).findFirst()
        realm.beginTransaction()
        dog!!.deleteFromRealm()
        realm.commitTransaction()
    }

    /**
     * query （查询所有）
     */
    fun queryAllDog(): List<UserBean> {
        val dogs = realm!!.where(UserBean::class.java).findAll()
        /**
         * 对查询结果，按Id进行排序，只能对查询结果进行排序
         */
        return realm.copyFromRealm(dogs)
    }

    /**
     * query （根据Id（主键）查）
     */
    fun queryDogById(id: String): UserBean? {
        return realm!!.where(UserBean::class.java).equalTo("Id", id).findFirst()
    }

    /**
     * query 当前用户
     */
    fun queryCurrentUser(isOnlin: Boolean): UserBean? {
        return realm!!.where(UserBean::class.java).equalTo("isOnlin", isOnlin).findFirst()
    }

    fun queryCurrentUserAll(): List<UserBean> {
        return realm!!.where(UserBean::class.java).findAll()
    }

    fun close() {
        realm?.close()
    }

    companion object {
        val DB_NAME = "myRealm.realm"
    }


}
