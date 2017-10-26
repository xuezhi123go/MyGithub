package com.gkzxhn.mygithub.di.component

import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import com.gkzxhn.mygithub.ui.fragment.ProfileFragment
import dagger.Subcomponent

/**
 * Created by 方 on 2017/10/24.
 */

@Subcomponent(modules = arrayOf(OAuthModule::class))
interface OAuthComponent {

    fun inject(profileFragment: ProfileFragment)

    fun inject(issueFragment: IssueFragment)
}