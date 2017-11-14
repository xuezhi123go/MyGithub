package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

/**
 * Created by 方 on 2017/10/19.
 *
 * User(login=FangforFun, id=23162290, avatar_url=https://avatars2.githubusercontent.com/u/23162290?v=4,
 *       gravatar_id=, url=https://api.github.com/users/FangforFun, html_url=https://github.com/FangforFun,
 *       followers_url=https://api.github.com/users/FangforFun/followers,
 *       following_url=https://api.github.com/users/FangforFun/following{/other_user},
 *       gists_url=https://api.github.com/users/FangforFun/gists{/gist_id},
 *       starred_url=https://api.github.com/users/FangforFun/starred{/owner}{/repo},
 *       subscriptions_url=https://api.github.com/users/FangforFun/subscriptions,
 *       organizations_url=https://api.github.com/users/FangforFun/orgs,
 *       repos_url=https://api.github.com/users/FangforFun/repos,
 *       events_url=https://api.github.com/users/FangforFun/events{/privacy},
 *       received_events_url=https://api.github.com/users/FangforFun/received_events,
 *       type=User, site_admin=false, name=null, company=null, blog=, location=null, email=null,
 *       hireable=false, bio=null, public_repos=8, public_gists=0, followers=1, following=1,
 *       created_at=2016-10-31T06:13:10Z, updated_at=2017-10-12T08:36:42Z, total_private_repos=0,
 *       owned_private_repos=0, private_gists=0, disk_usage=127160, collaborators=0,
 *       two_factor_authentication=false, plan=Plan(name=free, space=976562499, private_repos=0, collaborators=0))
 */

data class User(
		val login: String, //octocat
		val id: Int, //1
		var avatar_url: String, //https://github.com/images/error/octocat_happy.gif
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
		val site_admin: Boolean, //false
		val name: String, //monalisa octocat
		val company: String?, //GitHub
		val blog: String?, //https://github.com/blog
		val location: String, //San Francisco
		val email: String, //octocat@github.com
		val hireable: Boolean, //false
		val bio: String?, //There once was...
		val public_repos: Int, //2
		val public_gists: Int, //1
		val followers: Int, //20
		val following: Int, //0
		val created_at: String, //2008-01-14T04:33:35Z
		val updated_at: String, //2008-01-14T04:33:35Z
		val total_private_repos: Int, //100
		val owned_private_repos: Int, //100
		val private_gists: Int, //81
		val disk_usage: Int, //10000
		val collaborators: Int, //8
		val two_factor_authentication: Boolean, //true
		val plan: Plan?
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
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readString(),
			1 == source.readInt(),
			source.readString(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readString(),
			source.readString(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			1 == source.readInt(),
			source.readParcelable<Plan>(Plan::class.java.classLoader)
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
		writeString(if (TextUtils.isEmpty(name)) "" else name)
		writeString(if(TextUtils.isEmpty(company)) "" else company)
		writeString(if (TextUtils.isEmpty(blog)) "" else blog)
		writeString(if (TextUtils.isEmpty(location)) "" else location)
		writeString(if (TextUtils.isEmpty(email)) "" else email)
		writeInt((if (hireable) 1 else 0))
		writeString(if (TextUtils.isEmpty(bio)) "" else bio)
		writeInt(public_repos)
		writeInt(public_gists)
		writeInt(followers)
		writeInt(following)
		writeString(created_at)
		writeString(updated_at)
		writeInt(total_private_repos)
		writeInt(owned_private_repos)
		writeInt(private_gists)
		writeInt(disk_usage)
		writeInt(collaborators)
		writeInt((if (two_factor_authentication) 1 else 0))
		writeParcelable(if (null == plan) Plan("", 0, 0, 0)else plan, 0)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<User> = object : Parcelable.Creator<User> {
			override fun createFromParcel(source: Parcel): User = User(source)
			override fun newArray(size: Int): Array<User?> = arrayOfNulls(size)
		}
	}
}

data class Plan(
		val name: String, //Medium
		val space: Int, //400
		val private_repos: Int, //20
		val collaborators: Int //0
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readString(),
			source.readInt(),
			source.readInt(),
			source.readInt()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(name)
		writeInt(space)
		writeInt(private_repos)
		writeInt(collaborators)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Plan> = object : Parcelable.Creator<Plan> {
			override fun createFromParcel(source: Parcel): Plan = Plan(source)
			override fun newArray(size: Int): Array<Plan?> = arrayOfNulls(size)
		}
	}
}