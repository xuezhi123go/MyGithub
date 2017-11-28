package com.gkzxhn.mygithub.bean.entity

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by 方 on 2017/11/14.
 */

data class Icon2Name(
        val avatarUrl: String, //头像地址
        val name: String,//名字
        val type: String//对象类型
) : Parcelable {
    constructor(source: Parcel) : this(
            source.readString(),
            source.readString(),
            source.readString()
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeString(avatarUrl)
        writeString(name)
        writeString(type)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Icon2Name> = object : Parcelable.Creator<Icon2Name> {
            override fun createFromParcel(source: Parcel): Icon2Name = Icon2Name(source)
            override fun newArray(size: Int): Array<Icon2Name?> = arrayOfNulls(size)
        }
    }
}