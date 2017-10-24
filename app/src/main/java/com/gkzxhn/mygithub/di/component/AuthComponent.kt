package com.gkzxhn.mygithub.di.component

import com.gkzxhn.mygithub.di.module.AuthModule
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import dagger.Subcomponent

/**
 * Created by 方 on 2017/10/20.
 */

@Subcomponent(modules = arrayOf(AuthModule::class))
interface AuthComponent {

    fun inject(loginActivity: LoginActivity)
}