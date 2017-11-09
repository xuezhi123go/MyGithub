package com.gkzxhn.mygithub.di.component

import com.gkzxhn.mygithub.di.module.TrendingModule
import dagger.Subcomponent

/**
 * Created by 方 on 2017/10/20.
 */

@Subcomponent(modules = arrayOf(TrendingModule::class))
interface TrendingComponent {

//    fun inject(homeFragment: HomeFragment)
}