package com.gkzxhn.mygithub.di.module

import com.gkzxhn.balabala.mvp.contract.BaseView
import dagger.Module
import dagger.Provides

/**
 * Created by 方 on 2017/10/19.
 */

@Module
class AuthModule(private val mView: BaseView){


    @Provides fun getView() = mView

}