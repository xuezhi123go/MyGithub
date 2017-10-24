package com.gkzxhn.mygithub.di.module

import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthRetrofitClient
import com.gkzxhn.mygithub.constant.GithubConstant
import dagger.Module
import dagger.Provides

/**
 * Created by 方 on 2017/10/23.
 */

@Module
class OAuthModule constructor(private val view : BaseView) {

    @Provides
    fun provideOAuthRetrofit() = OAuthRetrofitClient.getInstance(baseUrl = GithubConstant.BASE_URL).createRetrofit()
}