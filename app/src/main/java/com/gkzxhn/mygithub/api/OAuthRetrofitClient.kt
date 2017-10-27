package com.gkzxhn.mygithub.api

import android.text.TextUtils
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.extension.getSharedPreference
import okhttp3.Interceptor

/**
 * Created by 方 on 2017/10/23.
 */
class OAuthRetrofitClient constructor(baseUrl: String): BaseRetrofitClient(baseUrl){


    override fun createInterceptor(vararg properties: String): Interceptor {
        val interceptor = Interceptor { chain ->
            val original = chain.request()
            val requestBuilder = original.newBuilder()
                    .header("Accept", "application/vnd.github.v3.json")
                    .header("User-Agent", "MyGithub")

            val token = SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.ACCESS_TOKEN, "")
            if (!TextUtils.isEmpty(token)) {
                requestBuilder
                        .header("Authorization", "token ${token}")
            }

            val request = requestBuilder.build()
            chain.proceed(request)
        }
        return interceptor
    }

    companion object{
        @Volatile
        var mInstance: OAuthRetrofitClient? = null

        fun getInstance(baseUrl: String) : OAuthRetrofitClient {
            if (mInstance == null) {
                synchronized(OAuthRetrofitClient::class) {
                    if (mInstance == null) {
                        mInstance = OAuthRetrofitClient(baseUrl)
                    }
                }
            }
            return mInstance!!
        }
    }
}