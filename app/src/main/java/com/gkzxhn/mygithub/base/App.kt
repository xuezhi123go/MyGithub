package com.gkzxhn.mygithub.base

import android.app.Application
import com.gkzxhn.mygithub.di.component.BaseComponent
import com.gkzxhn.mygithub.di.component.DaggerBaseComponent
import com.gkzxhn.mygithub.di.module.BaseModule
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/19.
 */
class App : Application() {

    @Inject lateinit var baseComponent: BaseComponent

    companion object{
        private lateinit var INSTANCE: App

        fun getInstance() = INSTANCE
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this

        setupComponent()
    }

    private fun setupComponent() {
        DaggerBaseComponent.builder()
                .baseModule(BaseModule(this))
//                .githubAuthModule(AuthModule())
                .build()
                .inject(this)
    }
}