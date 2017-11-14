package com.gkzxhn.mygithub.di.module

import android.content.Context
import com.gkzxhn.mygithub.api.TrendingApi
import com.gkzxhn.mygithub.constant.GithubConstant
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by 方 on 2017/10/19.
 */

@Module
class BaseModule(private val context: Context) {

    @Singleton
    @Provides fun provideGson() = GsonBuilder().create()


    @Singleton
    @Provides
    @Named("cache_client")
    fun provideOkhttp(): OkHttpClient {
        val cacheSize = 1024 * 1024 * 10L
        val cacheDir = File(context.cacheDir, "http")
        val cache = Cache(cacheDir, cacheSize)
        return OkHttpClient.Builder()
                .cache(cache)
                .connectTimeout(20, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .build()
    }

    @Provides
    @Singleton
    @Named("trending")
    fun provideRetrofit(@Named("cache_client")client: OkHttpClient, gson: Gson) =
            Retrofit.Builder()
                    .client(client)
                    .baseUrl(GithubConstant.Trending_URL)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                    .build()

    @Provides
    @Singleton
    fun provideTrendingApi(@Named("trending") retrofit : Retrofit) = retrofit.create(TrendingApi::class.java)

    @Provides
    @Singleton
    fun provideRxbus() = RxBus.instance
}