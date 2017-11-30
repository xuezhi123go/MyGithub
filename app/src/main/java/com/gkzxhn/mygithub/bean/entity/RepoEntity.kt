package com.gkzxhn.mygithub.bean.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by 方 on 2017/11/15.
 */
data class RepoEntity(
        val name: String,
        val full_name: String,
        val language: String?,
        val desc: String?,
        val star_count: Int,
        val fork_count: Int,
        val watch_count: Int
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString(),
            source.readString(),
            source.readInt(),
            source.readInt(),
            source.readInt()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(name)
        writeString(full_name)
        writeString(language)
        writeString(desc)
        writeInt(star_count)
        writeInt(fork_count)
        writeInt(watch_count)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<RepoEntity> = object : Parcelable.Creator<RepoEntity> {
            override fun createFromParcel(source: Parcel): RepoEntity = RepoEntity(source)
            override fun newArray(size: Int): Array<RepoEntity?> = arrayOfNulls(size)
        }
    }
}