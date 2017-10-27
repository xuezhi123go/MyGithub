package com.gkzxhn.mygithub.bean.info

/**
 * Created by Xuezhi on 2017/10/26.
 */

data class Stargazers(
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

data class Starred(
        val id: Int, //1296269
        val owner: Owner,
        val name: String, //Hello-World
        val full_name: String, //octocat/Hello-World
        val description: String, //This your first repo!
        val private: Boolean, //false
        val fork: Boolean, //false
        val url: String, //https://api.github.com/repos/octocat/Hello-World
        val html_url: String, //https://github.com/octocat/Hello-World
        val archive_url: String, //http://api.github.com/repos/octocat/Hello-World/{archive_format}{/ref}
        val assignees_url: String, //http://api.github.com/repos/octocat/Hello-World/assignees{/user}
        val blobs_url: String, //http://api.github.com/repos/octocat/Hello-World/git/blobs{/sha}
        val branches_url: String, //http://api.github.com/repos/octocat/Hello-World/branches{/branch}
        val clone_url: String, //https://github.com/octocat/Hello-World.git
        val collaborators_url: String, //http://api.github.com/repos/octocat/Hello-World/collaborators{/collaborator}
        val comments_url: String, //http://api.github.com/repos/octocat/Hello-World/comments{/number}
        val commits_url: String, //http://api.github.com/repos/octocat/Hello-World/commits{/sha}
        val compare_url: String, //http://api.github.com/repos/octocat/Hello-World/compare/{base}...{head}
        val contents_url: String, //http://api.github.com/repos/octocat/Hello-World/contents/{+path}
        val contributors_url: String, //http://api.github.com/repos/octocat/Hello-World/contributors
        val deployments_url: String, //http://api.github.com/repos/octocat/Hello-World/deployments
        val downloads_url: String, //http://api.github.com/repos/octocat/Hello-World/downloads
        val events_url: String, //http://api.github.com/repos/octocat/Hello-World/events
        val forks_url: String, //http://api.github.com/repos/octocat/Hello-World/forks
        val git_commits_url: String, //http://api.github.com/repos/octocat/Hello-World/git/commits{/sha}
        val git_refs_url: String, //http://api.github.com/repos/octocat/Hello-World/git/refs{/sha}
        val git_tags_url: String, //http://api.github.com/repos/octocat/Hello-World/git/tags{/sha}
        val git_url: String, //git:github.com/octocat/Hello-World.git
        val hooks_url: String, //http://api.github.com/repos/octocat/Hello-World/hooks
        val issue_comment_url: String, //http://api.github.com/repos/octocat/Hello-World/issues/comments{/number}
        val issue_events_url: String, //http://api.github.com/repos/octocat/Hello-World/issues/events{/number}
        val issues_url: String, //http://api.github.com/repos/octocat/Hello-World/issues{/number}
        val keys_url: String, //http://api.github.com/repos/octocat/Hello-World/keys{/key_id}
        val labels_url: String, //http://api.github.com/repos/octocat/Hello-World/labels{/name}
        val languages_url: String, //http://api.github.com/repos/octocat/Hello-World/languages
        val merges_url: String, //http://api.github.com/repos/octocat/Hello-World/merges
        val milestones_url: String, //http://api.github.com/repos/octocat/Hello-World/milestones{/number}
        val mirror_url: String, //git:git.example.com/octocat/Hello-World
        val notifications_url: String, //http://api.github.com/repos/octocat/Hello-World/notifications{?since, all, participating}
        val pulls_url: String, //http://api.github.com/repos/octocat/Hello-World/pulls{/number}
        val releases_url: String, //http://api.github.com/repos/octocat/Hello-World/releases{/id}
        val ssh_url: String, //git@github.com:octocat/Hello-World.git
        val stargazers_url: String, //http://api.github.com/repos/octocat/Hello-World/stargazers
        val statuses_url: String, //http://api.github.com/repos/octocat/Hello-World/statuses/{sha}
        val subscribers_url: String, //http://api.github.com/repos/octocat/Hello-World/subscribers
        val subscription_url: String, //http://api.github.com/repos/octocat/Hello-World/subscription
        val svn_url: String, //https://svn.github.com/octocat/Hello-World
        val tags_url: String, //http://api.github.com/repos/octocat/Hello-World/tags
        val teams_url: String, //http://api.github.com/repos/octocat/Hello-World/teams
        val trees_url: String, //http://api.github.com/repos/octocat/Hello-World/git/trees{/sha}
        val homepage: String, //https://github.com
        val language: Any, //null
        val forks_count: Int, //9
        val stargazers_count: Int, //80
        val watchers_count: Int, //80
        val size: Int, //108
        val default_branch: String, //master
        val open_issues_count: Int, //0
        val topics: List<String>,
        val has_issues: Boolean, //true
        val has_wiki: Boolean, //true
        val has_pages: Boolean, //false
        val has_downloads: Boolean, //true
        val pushed_at: String, //2011-01-26T19:06:43Z
        val created_at: String, //2011-01-26T19:01:12Z
        val updated_at: String, //2011-01-26T19:14:43Z
        val permissions: Permissions,
        val allow_rebase_merge: Boolean, //true
        val allow_squash_merge: Boolean, //true
        val allow_merge_commit: Boolean, //true
        val subscribers_count: Int, //42
        val network_count: Int //0
)

