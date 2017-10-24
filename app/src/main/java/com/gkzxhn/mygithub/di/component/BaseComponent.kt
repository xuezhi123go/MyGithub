package com.gkzxhn.mygithub.di.component

import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.di.module.AuthModule
import com.gkzxhn.mygithub.di.module.BaseModule
import com.gkzxhn.mygithub.di.module.OAuthModule
import dagger.Component
import javax.inject.Singleton

/**
 * Created by 方 on 2017/10/20.
 */

@Singleton
@Component(modules = arrayOf(BaseModule::class))
interface BaseComponent {

    fun plus(authModule: AuthModule): AuthComponent

    fun plus(oAuthModule: OAuthModule):OAuthComponent

    fun inject(app: App)
}