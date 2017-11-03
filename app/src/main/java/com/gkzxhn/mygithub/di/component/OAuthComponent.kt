package com.gkzxhn.mygithub.di.component

import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.ui.activity.EditIssueActivity
import com.gkzxhn.mygithub.ui.activity.IssueDetailActivity
import com.gkzxhn.mygithub.ui.activity.RepoListActivity
import com.gkzxhn.mygithub.ui.fragment.ContributorsFragment
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import com.gkzxhn.mygithub.ui.fragment.ProfileFragment
import com.gkzxhn.mygithub.ui.fragment.StarsFragment
import dagger.Subcomponent

/**
 * Created by 方 on 2017/10/24.
 */

@Subcomponent(modules = arrayOf(OAuthModule::class))
interface OAuthComponent {

    fun inject(profileFragment: ProfileFragment)

    fun inject(issueFragment: IssueFragment)

    fun inject(starsFragment: StarsFragment)

    fun inject(issueDetailActivity: IssueDetailActivity)

    fun inject(editIssueActivity: EditIssueActivity)

    fun inject(repoListActivity: RepoListActivity)

    fun inject(contributorsFragment: ContributorsFragment)
}