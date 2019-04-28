package com.frameworks.base.di.module

import android.app.Application
import com.frameworks.base.interfaces.IRepositoryManager
import com.google.gson.GsonBuilder
import com.jess.arms.integration.RepositoryManager
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
class AppModule(val application: Application) {
    @Provides
    @Singleton
    fun providesGson() = GsonBuilder().setDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").create()

    @Provides
    @Singleton
    fun providesApplication() = application

    @Provides
    @Singleton
    fun providesResources() = application.resources

    @Provides
    @Singleton
    fun providesIRepositoryManager(retrofit: Retrofit): IRepositoryManager{
        var repositoryManager = RepositoryManager()
        repositoryManager.mRetrofit = retrofit
        return repositoryManager
    }

}