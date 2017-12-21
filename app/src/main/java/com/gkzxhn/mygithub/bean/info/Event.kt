package com.gkzxhn.mygithub.bean.info

import android.os.Parcel
import android.os.Parcelable

/**
 * Created by Xuezhi on 2017/11/28.
 */

data class Event(
		val type: String, //Event
		val public: Boolean, //true
		val payload: IssueCommentEventPayload,
		val repo: RepoEvent,
		val actor: Actor,
		val org: Org,
		val created_at: String, //2011-09-06T17:26:27Z
		val id: String //12345
)

data class Org(
		val id: Int, //1
		val login: String, //github
		val gravatar_id: String,
		val url: String, //https://api.github.com/orgs/github
		val avatar_url: String //https://github.com/images/error/octocat_happy.gif
)

/*data class Payload(
        val test:String//这里没有内容，我乱填一个
)*/

data class RepoEvent(//这里原本是Repo，因为和其他数据类重名，所以改成RepoEvent
		val id: Int, //3
		val name: String, //octocat/Hello-World
		val url: String //https://api.github.com/repos/octocat/Hello-World
) : Parcelable {
    constructor(parcel: Parcel) : this(
            parcel.readInt(),
            parcel.readString(),
            parcel.readString()) {
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun describeContents(): Int {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    companion object CREATOR : Parcelable.Creator<RepoEvent> {
        override fun createFromParcel(parcel: Parcel): RepoEvent {
            return RepoEvent(parcel)
        }

        override fun newArray(size: Int): Array<RepoEvent?> {
            return arrayOfNulls(size)
        }
    }
}

data class Actor(
		val id: Int, //1
		val login: String, //octocat
		val gravatar_id: String,
		val avatar_url: String, //https://github.com/images/error/octocat_happy.gif
		val url: String //https://api.github.com/users/octocat
)