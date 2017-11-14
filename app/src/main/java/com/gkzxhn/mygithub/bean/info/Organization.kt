package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/10/27.
 */


data class Organization(
		val login: String, //github
		val id: Int, //1
		val url: String, //https://api.github.com/orgs/github
		val repos_url: String, //https://api.github.com/orgs/github/repos
		val events_url: String, //https://api.github.com/orgs/github/events
		val hooks_url: String, //https://api.github.com/orgs/github/hooks
		val issues_url: String, //https://api.github.com/orgs/github/issues
		val members_url: String, //https://api.github.com/orgs/github/members{/member}
		val public_members_url: String, //https://api.github.com/orgs/github/public_members{/member}
		val avatar_url: String, //https://github.com/images/error/octocat_happy.gif
		val description: String, //A great organization
		val name: String, //github
		val company: String, //GitHub
		val blog: String, //https://github.com/blog
		val location: String, //San Francisco
		val email: String, //octocat@github.com
		val public_repos: Int, //2
		val public_gists: Int, //1
		val followers: Int, //20
		val following: Int, //0
		val html_url: String, //https://github.com/octocat
		val created_at: String, //2008-01-14T04:33:35Z
		val type: String, //Organization
		val total_private_repos: Int, //100
		val owned_private_repos: Int, //100
		val private_gists: Int, //81
		val disk_usage: Int, //10000
		val collaborators: Int, //8
		val billing_email: String, //support@github.com
		val plan: Plan,
		val default_repository_settings: String, //read
		val members_can_create_repositories: Boolean //true
)
