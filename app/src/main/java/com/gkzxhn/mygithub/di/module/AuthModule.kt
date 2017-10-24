package com.gkzxhn.mygithub.di.module

import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.AccountApi
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import javax.inject.Named

/**
 * Created by 方 on 2017/10/19.
 */

@Module
class AuthModule(private val mView: BaseView,private val userCredentials: String?)/*(private var username : String = "FangforFun", private var password: String = "fyxgegededidi16")*/ {

//    @Provides
//    @Singleton
//    fun provideInterceptor() : Interceptor {
//        val interceptor = Interceptor { chain ->
//            // https://developer.github.com/v3/auth/#basic-authentication
//            // https://developer.github.com/v3/oauth/#non-web-application-flow
//
//            val basicAuth = "Basic " + String(Base64.encode(userCredentials!!.toByteArray(), Base64.DEFAULT))
//
//            val original = chain.request()
//
//            val requestBuilder = original.newBuilder()
//                    .header("Authorization", basicAuth.trim { it <= ' ' })
//
//            val request = requestBuilder.build()
//            chain.proceed(request)
//        }
//        return interceptor
//    }
//
//    @Singleton
//    @Provides
//    @Named("auth")
//    fun provideOkhttp(interceptor: Interceptor): OkHttpClient {
//        return OkHttpClient.Builder()
//                .addInterceptor(interceptor)
//                .connectTimeout(20, TimeUnit.SECONDS)
//                .writeTimeout(20, TimeUnit.SECONDS)
//                .build()
//    }
//
//    @Provides
//    @Singleton
//    @Named("auth")
//    fun provideRetrofit(client: OkHttpClient, gson: Gson) =
//            Retrofit.Builder()
//                    .client(client)
//                    .baseUrl(GithubConstant.BASE_URL)
//                    .addConverterFactory(GsonConverterFactory.create(gson))
//                    .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
//                    .build()

    @Provides
    fun provideAccountApi(@Named("auth") retrofit : Retrofit) = retrofit.create(AccountApi::class.java)

    @Provides fun getView() = mView

}