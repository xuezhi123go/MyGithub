package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/10/27.
 */


data class PostIssue(
		val title: String, //Found a bug
		val body: String, //I'm having a problem with this.
		val assignees: List<String>?,
		val milestone: Int?, //1
		val labels: List<String>?
)