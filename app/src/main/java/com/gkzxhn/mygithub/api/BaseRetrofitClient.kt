package com.gkzxhn.mygithub.api

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Created by 方 on 2017/10/23.
 */
abstract class BaseRetrofitClient constructor(baseUrl: String) {

    var okHttpClient : OkHttpClient? = null
    var retrofit : Retrofit? = null
    val DEFAULT_TIMEOUT : Long = 20
    var interceptor : Interceptor? = null
    var url = baseUrl

    fun createRetrofit(): Retrofit? {
        //创建interceptor
        createInterceptor()
        //okhttp创建
        createClient(interceptor)
        //retrofit创建
        retrofit = Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(url)
                .build()
        return retrofit
    }

    abstract fun  createInterceptor(vararg properties: String)

    protected fun createClient(interceptor: Interceptor?) {
        okHttpClient = OkHttpClient.Builder()
                .addInterceptor(interceptor)
                .connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS)
                .build()
    }
}