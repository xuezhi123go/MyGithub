package com.gkzxhn.mygithub.api

import android.util.Base64
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by 方 on 2017/10/23.
 */
class AuthorRetrofitClient private constructor(baseUrl:String) : BaseRetrofitClient(baseUrl){

    override fun createInterceptor(vararg properties: String): Interceptor {
        //创建interceptor
        return AuthorInterceptor("${properties[0]}:${properties[1]}")
    }

    companion object{
       @Volatile
        var mInstance: AuthorRetrofitClient? = null

        fun getInstance(baseUrl: String) : AuthorRetrofitClient {
            if (mInstance == null) {
                synchronized(AuthorRetrofitClient::class) {
                    if (mInstance == null) {
                        mInstance = AuthorRetrofitClient(baseUrl)
                    }
                }
            }
            return mInstance!!
        }
    }

    fun <T> create(service: Class<T>?): T? {
        if (service == null) {
            throw RuntimeException("Api service is null!")
        }
        return retrofit?.create(service)
    }

    class AuthorInterceptor constructor(val userCredentials: String) : Interceptor{

        override fun intercept(chain: Interceptor.Chain?): Response {
            // https://developer.github.com/v3/auth/#basic-authentication
            // https://developer.github.com/v3/oauth/#non-web-application-flow
//            val userCredentials = "${properties[0]} : ${properties[1]}"

            val basicAuth = "Basic " + String(Base64.encode(userCredentials.toByteArray(), Base64.DEFAULT))

            val original = chain!!.request()

            val requestBuilder = original.newBuilder()
                    .header("Authorization", basicAuth.trim { it <= ' ' })

            val request = requestBuilder.build()
            return chain.proceed(request)
        }

    }
}