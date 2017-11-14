package com.gkzxhn.mygithub.di.module

import com.gkzxhn.balabala.mvp.contract.BaseView
import dagger.Module
import dagger.Provides

/**
 * Created by 方 on 2017/11/9.
 */

@Module
class TrendingModule constructor(private val mView: BaseView){

    @Provides
    fun getView() = mView

}