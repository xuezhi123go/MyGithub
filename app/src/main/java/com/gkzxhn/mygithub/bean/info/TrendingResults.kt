package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by 方 on 2017/11/9.
 */


data class TrendingResults(
		val count: Int, //3
        val items: List<ItemBean>,
        val msg: String //suc
)

data class Items(
		val count: Int, //25
		val items: List<ItemBean>,
		val msg: String //done
)

data class
ItemBean(
        val added_stars: String, //124 stars today
        val avatars: List<String>,
        val desc: String, //A secret manager for AWS
        val forks: String, //7
        val lang: String, //Java
        val repo: String, //schibsted/strongbox
        val repo_link: String, //https://github.com/schibsted/strongbox
        val stars: String //134
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.createStringArrayList(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(added_stars)
        writeStringList(avatars)
        writeString(desc)
        writeString(forks)
        writeString(lang)
        writeString(repo)
        writeString(repo_link)
        writeString(stars)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<ItemBean> = object : Parcelable.Creator<ItemBean> {
            override fun createFromParcel(source: Parcel): ItemBean = ItemBean(source)
            override fun newArray(size: Int): Array<ItemBean?> = arrayOfNulls(size)
        }
    }
}
