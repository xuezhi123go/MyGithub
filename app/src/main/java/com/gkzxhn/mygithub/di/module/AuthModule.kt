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
class AuthModule(private val mView: BaseView)/*(private var username : String = "FangforFun", private var password: String = "fyxgegededidi16")*/ {

    @Provides
    fun provideAccountApi(@Named("auth") retrofit : Retrofit) = retrofit.create(AccountApi::class.java)

    @Provides fun getView() = mView

}