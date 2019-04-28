package com.frameworks.base.di.module

import android.app.Application
import com.frameworks.BuildConfig
import com.frameworks.base.http.CommonInterceptor
import com.frameworks.base.http.GlobalHttpHandler
import com.frameworks.base.http.GlobalHttpHandlerImp
import com.frameworks.base.http.GsonConverterFactory
import com.frameworks.base.http.log.DefaultFormatPrinter
import com.frameworks.base.http.log.FormatPrinter
import com.frameworks.base.http.log.RequestInterceptor
import com.frameworks.base.http.log.RequestInterceptor.Level
import com.frameworks.base.utils.CodeAppUtils
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton
@Module
class ClientModule {

    private fun getBaseBuilder(cache: Cache): OkHttpClient.Builder {
        return OkHttpClient.Builder()
                .addNetworkInterceptor(RequestInterceptor())
                .addInterceptor(CommonInterceptor())
                .cache(cache)
                .retryOnConnectionFailure(true)
                .connectTimeout(120, TimeUnit.SECONDS)
                .readTimeout(120, TimeUnit.SECONDS)
                .writeTimeout(120, TimeUnit.SECONDS)
    }

    private class CachingControlInterceptor : Interceptor {
        override fun intercept(chain: Interceptor.Chain): okhttp3.Response {

            val requestBuilder = chain.request().newBuilder()
            val cacheControl = CacheControl.Builder()
                    .maxStale(1, TimeUnit.MINUTES)
                    .maxAge(1, TimeUnit.MINUTES)
                    .build()

            requestBuilder.cacheControl(cacheControl)
            requestBuilder.header("Content-Type", "application/json")
            val request = requestBuilder.build()

            val originalResponse = chain.proceed(request)
            return originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .removeHeader("Cache-Control")
                    .header("Cache-Control", "public, only-if-cached, max-stale=604800")
                    .build()
        }
    }

    @Provides
    @Singleton
    fun providesOkHttpCache(pApplication: Application): Cache =
            Cache(pApplication.cacheDir, 10 * 1024 * 1024)

    @Provides
    @Singleton
    fun providesPrintLevel(): Level {
        return Level.ALL
    }

    @Provides
    @Singleton
    fun providesFormatPrinter(): FormatPrinter =  DefaultFormatPrinter()

    @Provides
    @Singleton
    fun providesGlobalHttpHandler(): GlobalHttpHandler =  GlobalHttpHandlerImp()

    @Provides
    @Singleton
    fun providesRequestInterceptor(level:Level ,formatPrinter: FormatPrinter,globalHttpHandler : GlobalHttpHandler) : RequestInterceptor {
        val interceptor = RequestInterceptor()
        interceptor.printLevel = level
        interceptor.mHandler = globalHttpHandler
        interceptor.mPrinter = formatPrinter
        return interceptor
    }



    @Provides
    @Singleton
    fun providesOkHttp(cache: Cache, loggingInterceptor: RequestInterceptor) = getBaseBuilder(cache)
            .addNetworkInterceptor(CachingControlInterceptor())
            .addInterceptor(loggingInterceptor)
            .build()


    @Provides
    @Singleton
    fun providesRetrofit(client: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder().baseUrl(CodeAppUtils.mApplication!!.getUrls())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())//使用 Rxjava
            .addConverterFactory(GsonConverterFactory.create(gson))//使用 Gson
            .client(client)
            .build()
}