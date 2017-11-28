package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

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
		val language: String, //null
		val has_issues: Boolean, //false
		val has_projects: Boolean, //true
		val has_downloads: Boolean, //true
		val has_wiki: Boolean, //true
		val has_pages: Boolean, //false
		val forks_count: Int, //0
		val mirror_url: String, //null
		val archived: Boolean, //false
		val open_issues_count: Int, //0
		val forks: Int, //0
		val open_issues: Int, //0
		val watchers: Int, //0
		val default_branch: String, //master
		val permissions: Permissions
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readInt(),
			source.readString(),
			source.readString(),
			source.readParcelable<Owner>(Owner::class.java.classLoader),
			1 == source.readInt(),
			source.readString(),
			source.readString(),
			1 == source.readInt(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			/*if (TextUtils.isEmpty(source.readString())) "" else */source.readString(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			/*if (TextUtils.isEmpty(source.readString())) "" else */source.readString(),
			1 == source.readInt(),
			1 == source.readInt(),
			1 == source.readInt(),
			1 == source.readInt(),
			1 == source.readInt(),
			source.readInt(),
			/*if(TextUtils.isEmpty(source.readString())) "" else */source.readString(),
			1 == source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readString(),
			source.readParcelable<Permissions>(Permissions::class.java.classLoader)
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeInt(id)
		writeString(name)
		writeString(full_name)
		writeParcelable(owner, 0)
		writeInt((if (private) 1 else 0))
		writeString(html_url)
		writeString(if (TextUtils.isEmpty(description)) "" else description)
		writeInt((if (fork) 1 else 0))
		writeString(url)
		writeString(forks_url)
		writeString(keys_url)
		writeString(collaborators_url)
		writeString(teams_url)
		writeString(hooks_url)
		writeString(issue_events_url)
		writeString(events_url)
		writeString(assignees_url)
		writeString(branches_url)
		writeString(tags_url)
		writeString(blobs_url)
		writeString(git_tags_url)
		writeString(git_refs_url)
		writeString(trees_url)
		writeString(statuses_url)
		writeString(languages_url)
		writeString(stargazers_url)
		writeString(contributors_url)
		writeString(subscribers_url)
		writeString(subscription_url)
		writeString(commits_url)
		writeString(git_commits_url)
		writeString(comments_url)
		writeString(issue_comment_url)
		writeString(contents_url)
		writeString(compare_url)
		writeString(merges_url)
		writeString(archive_url)
		writeString(downloads_url)
		writeString(issues_url)
		writeString(pulls_url)
		writeString(milestones_url)
		writeString(notifications_url)
		writeString(labels_url)
		writeString(releases_url)
		writeString(deployments_url)
		writeString(created_at)
		writeString(updated_at)
		writeString(pushed_at)
		writeString(git_url)
		writeString(ssh_url)
		writeString(clone_url)
		writeString(svn_url)
		writeString(if (TextUtils.isEmpty(homepage)) "" else homepage)
		writeInt(size)
		writeInt(stargazers_count)
		writeInt(watchers_count)
		writeString(if(TextUtils.isEmpty(language)) "" else language)
		writeInt((if (has_issues) 1 else 0))
		writeInt((if (has_projects) 1 else 0))
		writeInt((if (has_downloads) 1 else 0))
		writeInt((if (has_wiki) 1 else 0))
		writeInt((if (has_pages) 1 else 0))
		writeInt(forks_count)
		writeString(if(TextUtils.isEmpty(mirror_url)) "" else mirror_url)
		writeInt((if (archived) 1 else 0))
		writeInt(open_issues_count)
		writeInt(forks)
		writeInt(open_issues)
		writeInt(watchers)
		writeString(default_branch)
		writeParcelable(permissions, 0)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Repo> = object : Parcelable.Creator<Repo> {
			override fun createFromParcel(source: Parcel): Repo = Repo(source)
			override fun newArray(size: Int): Array<Repo?> = arrayOfNulls(size)
		}
	}
}

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
        val site_admin: Boolean, //false
        val score: Float //1
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readInt(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            1 == source.readInt(),
            source.readFloat()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(login)
        writeInt(id)
        writeString(avatar_url)
        writeString(gravatar_id)
        writeString(url)
        writeString(html_url)
        writeString(followers_url)
        writeString(following_url)
        writeString(gists_url)
        writeString(starred_url)
        writeString(subscriptions_url)
        writeString(organizations_url)
        writeString(repos_url)
        writeString(events_url)
        writeString(received_events_url)
        writeString(type)
        writeInt((if (site_admin) 1 else 0))
        writeFloat(score)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Owner> = object : Parcelable.Creator<Owner> {
            override fun createFromParcel(source: Parcel): Owner = Owner(source)
            override fun newArray(size: Int): Array<Owner?> = arrayOfNulls(size)
        }
    }
}

data class Permissions(
		val admin: Boolean, //true
		val push: Boolean, //true
		val pull: Boolean //true
) : Parcelable {
	constructor(source: Parcel) : this(
			1 == source.readInt(),
			1 == source.readInt(),
			1 == source.readInt()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeInt((if (admin) 1 else 0))
		writeInt((if (push) 1 else 0))
		writeInt((if (pull) 1 else 0))
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Permissions> = object : Parcelable.Creator<Permissions> {
			override fun createFromParcel(source: Parcel): Permissions = Permissions(source)
			override fun newArray(size: Int): Array<Permissions?> = arrayOfNulls(size)
		}
	}
}