package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable
import android.text.TextUtils

/**
 * Created by 方 on 2017/11/9.
 */


data class TrendingResults(
		val count: Int, //25
		val msg: String, //done
		val items: List<TrendingItem>
) : Parcelable {
	constructor(source: Parcel) : this(
			source.readInt(),
			source.readString(),
			source.createTypedArrayList(TrendingItem.CREATOR)
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeInt(count)
		writeString(msg)
		writeTypedList(items)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<TrendingResults> = object : Parcelable.Creator<TrendingResults> {
			override fun createFromParcel(source: Parcel): TrendingResults = TrendingResults(source)
			override fun newArray(size: Int): Array<TrendingResults?> = arrayOfNulls(size)
		}
	}
}

data class TrendingItem(
		val user: String, //google
		val user_link: String, //https://github.com/google
		val full_name: String, //(Google)
		val developer_avatar: String, //https://avatars0.githubusercontent.com/u/1342004?s=96&v=4

		val repo: String, //tipsy/javalin
		val repo_link: String, //https://github.com/tipsy/javalin
		val desc: String, //A Simple REST API Library for Java/Kotlin
		val lang: String, //Java
		val stars: String, //698
		val forks: String, //38
		val avatars: List<String>,
		val added_stars: String //187 stars today
) : Parcelable {
	constructor(source: Parcel) : this(
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
			source.createStringArrayList(),
			source.readString()
	)

	override fun describeContents() = 0

	override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
		writeString(if (TextUtils.isEmpty(user)) "" else user)
		writeString(if (TextUtils.isEmpty(user_link)) "" else user_link)
		writeString(if (TextUtils.isEmpty(full_name)) "" else full_name)
		writeString(if (TextUtils.isEmpty(developer_avatar)) "" else developer_avatar)
		writeString(if (TextUtils.isEmpty(repo)) "" else repo)
		writeString(if (TextUtils.isEmpty(repo_link)) "" else repo_link)
		writeString(if (TextUtils.isEmpty(desc)) "" else desc)
		writeString(if (TextUtils.isEmpty(lang)) "" else lang)
		writeString(if (TextUtils.isEmpty(stars)) "" else stars)
		writeString(if (TextUtils.isEmpty(forks)) "" else forks)
		writeStringList(avatars)
		writeString(if (TextUtils.isEmpty(added_stars)) "" else added_stars)
	}

	companion object {
		@JvmField
		val CREATOR: Parcelable.Creator<TrendingItem> = object : Parcelable.Creator<TrendingItem> {
			override fun createFromParcel(source: Parcel): TrendingItem = TrendingItem(source)
			override fun newArray(size: Int): Array<TrendingItem?> = arrayOfNulls(size)
		}
	}
}

