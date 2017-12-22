package com.gkzxhn.mygithub.di.component

import com.gkzxhn.balabala.ui.activity.MainActivity
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.ui.activity.*
import com.gkzxhn.mygithub.ui.fragment.*
import dagger.Subcomponent

/**
 * Created by 方 on 2017/10/24.
 */

@Subcomponent(modules = arrayOf(OAuthModule::class))
interface OAuthComponent {

    fun inject(profileFragment: ProfileFragment)

    fun inject(issueFragment: IssueFragment)

    fun inject(starsFragment: StarsFragment)

    fun inject(notificationsFragment: NotificationsFragment)

    fun inject(issueDetailActivity: IssueDetailActivity)

    fun inject(editIssueActivity: EditIssueActivity)

    fun inject(repoListActivity: RepoListActivity)

    fun inject(contributorsFragment: ContributorsFragment)

    fun inject(userActivity: UserActivity)

    fun inject(homeFragment: HomeFragment)

    fun inject(repoDetailActivity: RepoDetailActivity)

    fun inject(eventFragment: EventFragment)

    fun inject(searchActivity: SearchActivity)

    fun inject(mainActivity: MainActivity)

    fun inject(activityFragment: ActivityFragment)

    fun inject(fileListFragment: FileListFragment)

    fun inject(fileDetailActivity: FileDetailActivity)
}