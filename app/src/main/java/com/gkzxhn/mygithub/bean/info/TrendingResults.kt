package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/11/9.
 */


data class TrendingResults(
		val count: Int, //25
		val msg: String, //done
		val items: List<TrendingItem>
)

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
)

