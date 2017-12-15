package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by 方 on 2017/11/8.
 */

data class SearchUserResult(
		val total_count: Int, //39
		val incomplete_results: Boolean, //false
		val items: ArrayList<Owner>
)

data class Item(
		val login: String, //torvalds
		val id: Int, //1024025
		val avatar_url: String, //https://avatars0.githubusercontent.com/u/1024025?v=4
		val gravatar_id: String,
		val url: String, //https://api.github.com/users/torvalds
		val html_url: String, //https://github.com/torvalds
		val followers_url: String, //https://api.github.com/users/torvalds/followers
		val following_url: String, //https://api.github.com/users/torvalds/following{/other_user}
		val gists_url: String, //https://api.github.com/users/torvalds/gists{/gist_id}
		val starred_url: String, //https://api.github.com/users/torvalds/starred{/owner}{/repo}
		val subscriptions_url: String, //https://api.github.com/users/torvalds/subscriptions
		val organizations_url: String, //https://api.github.com/users/torvalds/orgs
		val repos_url: String, //https://api.github.com/users/torvalds/repos
		val events_url: String, //https://api.github.com/users/torvalds/events{/privacy}
		val received_events_url: String, //https://api.github.com/users/torvalds/received_events
		val type: String, //USER
		val site_admin: Boolean, //false
		val score: Float //1.0
)


data class SearchRepoResult(
		val total_count: Int, //40
		val incomplete_results: Boolean, //false
		val items: ArrayList<Repo>
)

data class RepoItem(
		val id: Int, //3081286
		val name: String, //Tetris
		val full_name: String, //dtrupenn/Tetris
		val owner: Owner,
		val private: Boolean, //false
		val html_url: String, //https://github.com/dtrupenn/Tetris
		val description: String, //A C implementation of Tetris using Pennsim through LC4
		val fork: Boolean, //false
		val url: String, //https://api.github.com/repos/dtrupenn/Tetris
		val created_at: String, //2012-01-01T00:31:50Z
		val updated_at: String, //2013-01-05T17:58:47Z
		val pushed_at: String, //2012-01-01T00:37:02Z
		val homepage: String,
		val size: Int, //524
		val stargazers_count: Int, //1
		val watchers_count: Int, //1
		val language: String, //Assembly
		val forks_count: Int, //0
		val open_issues_count: Int, //0
		val master_branch: String, //master
		val default_branch: String, //master
		val score: Double //10.309712
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
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readString(),
			source.readInt(),
			source.readInt(),
			source.readString(),
			source.readString(),
			source.readDouble()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeInt(id)
		writeString(name)
		writeString(full_name)
		writeParcelable(owner, 0)
		writeInt((if (private) 1 else 0))
		writeString(html_url)
		writeString(description)
		writeInt((if (fork) 1 else 0))
		writeString(url)
		writeString(created_at)
		writeString(updated_at)
		writeString(pushed_at)
		writeString(homepage)
		writeInt(size)
		writeInt(stargazers_count)
		writeInt(watchers_count)
		writeString(language)
		writeInt(forks_count)
		writeInt(open_issues_count)
		writeString(master_branch)
		writeString(default_branch)
		writeDouble(score)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<RepoItem> = object : Parcelable.Creator<RepoItem> {
			override fun createFromParcel(source: Parcel): RepoItem = RepoItem(source)
			override fun newArray(size: Int): Array<RepoItem?> = arrayOfNulls(size)
		}
	}
}