package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/10/23.
 */



data class Repo(
		val id: Int, //78227816
		val name: String, //AndroidGuide
		val full_name: String, //FangforFun/AndroidGuide
		val owner: Owner,
		val private: Boolean, //false
		val html_url: String, //https://github.com/FangforFun/AndroidGuide
		val description: String, //AndroidCat安卓书签网
		val fork: Boolean, //true
		val url: String, //https://api.github.com/repos/FangforFun/AndroidGuide
		val forks_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/forks
		val keys_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/keys{/key_id}
		val collaborators_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/collaborators{/collaborator}
		val teams_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/teams
		val hooks_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/hooks
		val issue_events_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/issues/events{/number}
		val events_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/events
		val assignees_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/assignees{/user}
		val branches_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/branches{/branch}
		val tags_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/tags
		val blobs_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/git/blobs{/sha}
		val git_tags_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/git/tags{/sha}
		val git_refs_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/git/refs{/sha}
		val trees_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/git/trees{/sha}
		val statuses_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/statuses/{sha}
		val languages_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/languages
		val stargazers_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/stargazers
		val contributors_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/contributors
		val subscribers_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/subscribers
		val subscription_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/subscription
		val commits_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/commits{/sha}
		val git_commits_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/git/commits{/sha}
		val comments_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/comments{/number}
		val issue_comment_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/issues/comments{/number}
		val contents_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/contents/{+path}
		val compare_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/compare/{base}...{head}
		val merges_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/merges
		val archive_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/{archive_format}{/ref}
		val downloads_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/downloads
		val issues_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/issues{/number}
		val pulls_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/pulls{/number}
		val milestones_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/milestones{/number}
		val notifications_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/notifications{?since,all,participating}
		val labels_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/labels{/name}
		val releases_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/releases{/id}
		val deployments_url: String, //https://api.github.com/repos/FangforFun/AndroidGuide/deployments
		val created_at: String, //2017-01-06T18:11:05Z
		val updated_at: String, //2017-01-04T09:30:35Z
		val pushed_at: String, //2016-05-05T06:55:36Z
		val git_url: String, //git://github.com/FangforFun/AndroidGuide.git
		val ssh_url: String, //git@github.com:FangforFun/AndroidGuide.git
		val clone_url: String, //https://github.com/FangforFun/AndroidGuide.git
		val svn_url: String, //https://github.com/FangforFun/AndroidGuide
		val homepage: String, //http://www.androidcat.com/
		val size: Int, //129
		val stargazers_count: Int, //0
		val watchers_count: Int, //0
		val language: Any, //null
		val has_issues: Boolean, //false
		val has_projects: Boolean, //true
		val has_downloads: Boolean, //true
		val has_wiki: Boolean, //true
		val has_pages: Boolean, //false
		val forks_count: Int, //0
		val mirror_url: Any, //null
		val archived: Boolean, //false
		val open_issues_count: Int, //0
		val forks: Int, //0
		val open_issues: Int, //0
		val watchers: Int, //0
		val default_branch: String, //master
		val permissions: Permissions
)

data class Owner(
		val login: String, //FangforFun
		val id: Int, //23162290
		val avatar_url: String, //https://avatars2.githubusercontent.com/u/23162290?v=4
		val gravatar_id: String,
		val url: String, //https://api.github.com/users/FangforFun
		val html_url: String, //https://github.com/FangforFun
		val followers_url: String, //https://api.github.com/users/FangforFun/followers
		val following_url: String, //https://api.github.com/users/FangforFun/following{/other_user}
		val gists_url: String, //https://api.github.com/users/FangforFun/gists{/gist_id}
		val starred_url: String, //https://api.github.com/users/FangforFun/starred{/owner}{/repo}
		val subscriptions_url: String, //https://api.github.com/users/FangforFun/subscriptions
		val organizations_url: String, //https://api.github.com/users/FangforFun/orgs
		val repos_url: String, //https://api.github.com/users/FangforFun/repos
		val events_url: String, //https://api.github.com/users/FangforFun/events{/privacy}
		val received_events_url: String, //https://api.github.com/users/FangforFun/received_events
		val type: String, //User
		val site_admin: Boolean //false
)

data class Permissions(
		val admin: Boolean, //true
		val push: Boolean, //true
		val pull: Boolean //true
)