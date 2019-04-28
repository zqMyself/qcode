package com.xiangjiale.zaoplanet.utils

import com.xiangjiale.zaoplanet.module.welcome.bean.UserBean

class UserDataUtils {
    var mUserBean                      = ArrayList<UserBean>()

    companion object {
        var intance  : UserDataUtils ? = null
              get() = if (intance == null ) UserDataUtils() else intance
    }

    fun  setUserData(userBean : UserBean){
        mUserBean.clear()
        mUserBean.add(userBean)
    }

    fun getUserData() : UserBean?{
        if (mUserBean.size > 0 ){
            return mUserBean[0]
        }
        return UserBean()
    }



}