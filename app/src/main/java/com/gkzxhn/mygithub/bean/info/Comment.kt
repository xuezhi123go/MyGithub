package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/10/26.
 */



data class Comment(
		val url: String, //https://api.github.com/repos/FangforFun/MyGithub/issues/comments/339274697
		val html_url: String, //https://github.com/FangforFun/MyGithub/issues/2#issuecomment-339274697
		val issue_url: String, //https://api.github.com/repos/FangforFun/MyGithub/issues/2
		val id: Int, //339274697
		val user: User,
		val created_at: String, //2017-10-25T09:42:14Z
		val updated_at: String, //2017-10-25T09:42:14Z
		val author_association: String, //OWNER
		val body: String //?????test test
)