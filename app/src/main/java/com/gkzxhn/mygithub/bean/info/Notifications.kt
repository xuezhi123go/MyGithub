package com.gkzxhn.mygithub.bean.info

/**
 * Created by Xuezhi on 2017/11/15.
 */

data class Notifications(
        val id: String, //1
        val repository: Repository,
        val subject: Subject,
        val reason: String, //subscribed
        val unread: Boolean, //true
        val updated_at: String, //2014-11-07T22:01:45Z
        val last_read_at: String, //2014-11-07T22:01:45Z
        val url: String //https://api.github.com/notifications/threads/1
)

data class Subject(
        val title: String, //Greetings
        val url: String, //https://api.github.com/repos/octokit/octokit.rb/issues/123
        val latest_comment_url: String, //https://api.github.com/repos/octokit/octokit.rb/issues/comments/123
        val type: String //Issue
)

data class Repository(
        val id: Int, //1296269
        val owner: Owner,
        val name: String, //Hello-World
        val full_name: String, //octocat/Hello-World
        val description: String, //This your first repo!
        val private: Boolean, //false
        val fork: Boolean, //false
        val url: String, //https://api.github.com/repos/octocat/Hello-World
        val html_url: String //https://github.com/octocat/Hello-World
)

/*
data class Owner(
		val login: String, //octocat
		val id: Int, //1
		val avatar_url: String, //https://github.com/images/error/octocat_happy.gif
		val gravatar_id: String,
		val url: String, //https://api.github.com/users/octocat
		val html_url: String, //https://github.com/octocat
		val followers_url: String, //https://api.github.com/users/octocat/followers
		val following_url: String, //https://api.github.com/users/octocat/following{/other_user}
		val gists_url: String, //https://api.github.com/users/octocat/gists{/gist_id}
		val starred_url: String, //https://api.github.com/users/octocat/starred{/owner}{/repo}
		val subscriptions_url: String, //https://api.github.com/users/octocat/subscriptions
		val organizations_url: String, //https://api.github.com/users/octocat/orgs
		val repos_url: String, //https://api.github.com/users/octocat/repos
		val events_url: String, //https://api.github.com/users/octocat/events{/privacy}
		val received_events_url: String, //https://api.github.com/users/octocat/received_events
		val type: String, //User
		val site_admin: Boolean //false
)*/

