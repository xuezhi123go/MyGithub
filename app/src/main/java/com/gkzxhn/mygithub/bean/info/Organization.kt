package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

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
		val plan: Plan?,
		val default_repository_settings: String, //read
		val members_can_create_repositories: Boolean //true
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
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readString(),
			source.readString(),
			source.readString(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readInt(),
			source.readString(),
			source.readParcelable<Plan?>(Plan::class.java.classLoader),
			source.readString(),
			1 == source.readInt()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(login)
		writeInt(id)
		writeString(if (TextUtils.isEmpty(url)) "" else url)
		writeString(if (TextUtils.isEmpty(repos_url)) "" else repos_url)
		writeString(if (TextUtils.isEmpty(events_url)) "" else events_url)
		writeString(if (TextUtils.isEmpty(hooks_url)) "" else hooks_url)
		writeString(if (TextUtils.isEmpty(issues_url)) "" else issues_url)
		writeString(if (TextUtils.isEmpty(members_url)) "" else members_url)
		writeString(if (TextUtils.isEmpty(public_members_url)) "" else public_members_url)
		writeString(avatar_url)
		writeString(if (TextUtils.isEmpty(description)) "" else description)
		writeString(if (TextUtils.isEmpty(name)) "" else name)
		writeString(if (TextUtils.isEmpty(company)) "" else company)
		writeString(if (TextUtils.isEmpty(blog)) "" else blog)
		writeString(if (TextUtils.isEmpty(location)) "" else location)
		writeString(if (TextUtils.isEmpty(email)) "" else email)
		writeInt(public_repos)
		writeInt(public_gists)
		writeInt(followers)
		writeInt(following)
		writeString(if (TextUtils.isEmpty(html_url)) "" else html_url)
		writeString(if (TextUtils.isEmpty(created_at)) "" else created_at)
		writeString(if (TextUtils.isEmpty(type)) "" else type)
		writeInt(total_private_repos)
		writeInt(owned_private_repos)
		writeInt(private_gists)
		writeInt(disk_usage)
		writeInt(collaborators)
		writeString(if (TextUtils.isEmpty(billing_email)) "" else billing_email)
		writeParcelable(plan, 0)
		writeString(if (TextUtils.isEmpty(default_repository_settings)) "" else default_repository_settings)
		writeInt((if (members_can_create_repositories) 1 else 0))
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<Organization> = object : Parcelable.Creator<Organization> {
			override fun createFromParcel(source: Parcel): Organization = Organization(source)
			override fun newArray(size: Int): Array<Organization?> = arrayOfNulls(size)
		}
	}
}
