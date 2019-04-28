package com.frameworks.test

import com.frameworks.base.core.BaseEntry
import io.reactivex.Observable
import retrofit2.http.FieldMap
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface CodeApiService  {
    //启动页资源
    @JvmSuppressWildcards
    @FormUrlEncoded
    @POST("/api/framework/splashPicId")
    fun getSplashPicId(@FieldMap params: Map<String, Any>): Observable<BaseEntry<List<String>>>
}