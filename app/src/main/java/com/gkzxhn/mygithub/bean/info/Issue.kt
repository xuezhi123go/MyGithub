package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/10/25.
 */


data class Issue(
		val id: Int, //1
		val url: String, //https://api.github.com/repos/octocat/Hello-World/issues/1347
		val repository_url: String, //https://api.github.com/repos/octocat/Hello-World
		val labels_url: String, //https://api.github.com/repos/octocat/Hello-World/issues/1347/labels{/name}
		val comments_url: String, //https://api.github.com/repos/octocat/Hello-World/issues/1347/comments
		val events_url: String, //https://api.github.com/repos/octocat/Hello-World/issues/1347/events
		val html_url: String, //https://github.com/octocat/Hello-World/issues/1347
		val number: Int, //1347
		val state: String, //open
		val title: String, //Found a bug
		val body: String, //I'm having a problem with this.
		val user: Owner,
		val labels: List<Label>,
		val assignee: Assignee,
		val assignees: List<Assignee>,
		val milestone: Milestone,
		val locked: Boolean, //false
		val comments: Int, //0
		val pull_request: PullRequest,
		val closed_at: Any, //null
		val created_at: String, //2011-04-22T13:33:48Z
		val updated_at: String //2011-04-22T13:33:48Z
)

data class UserData(
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
)

data class Label(
		val id: Int, //208045946
		val url: String, //https://api.github.com/repos/octocat/Hello-World/labels/bug
		val name: String, //bug
		val color: String, //f29513
		val default: Boolean //true
)

data class Milestone(
		val url: String, //https://api.github.com/repos/octocat/Hello-World/milestones/1
		val html_url: String, //https://github.com/octocat/Hello-World/milestones/v1.0
		val labels_url: String, //https://api.github.com/repos/octocat/Hello-World/milestones/1/labels
		val id: Int, //1002604
		val number: Int, //1
		val state: String, //open
		val title: String, //v1.0
		val description: String, //Tracking milestone for version 1.0
		val creator: Creator,
		val open_issues: Int, //4
		val closed_issues: Int, //8
		val created_at: String, //2011-04-10T20:09:31Z
		val updated_at: String, //2014-03-03T18:58:10Z
		val closed_at: String, //2013-02-12T13:22:01Z
		val due_on: String //2012-10-09T23:39:01Z
)

data class Creator(
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
)

data class Assignee(
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
)

data class PullRequest(
		val url: String, //https://api.github.com/repos/octocat/Hello-World/pulls/1347
		val html_url: String, //https://github.com/octocat/Hello-World/pull/1347
		val diff_url: String, //https://github.com/octocat/Hello-World/pull/1347.diff
		val patch_url: String //https://github.com/octocat/Hello-World/pull/1347.patch
)