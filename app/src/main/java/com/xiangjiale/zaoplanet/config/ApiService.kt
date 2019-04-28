package com.xiangjiale.zaoplanet.config

import com.xiangjiale.zaoplanet.module.welcome.bean.UserInfoBean
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface ApiService  {

    //刷新登录
    @FormUrlEncoded
    @JvmSuppressWildcards
    @POST("api/planet/refreshLogin")
    fun getRefreshLogin(@FieldMap params: Map<String,Any>): Observable<UserInfoBean>

}