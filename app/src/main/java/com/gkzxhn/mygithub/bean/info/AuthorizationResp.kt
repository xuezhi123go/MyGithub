package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/10/19.
 */

data class AuthorizationResp(
		val id: Int, //1
		val url: String, //https://api.github.com/authorizations/1
		val scopes: List<String>,
		val token: String, //abcdefgh12345678
		val token_last_eight: String, //12345678
		val hashed_token: String, //25f94a2a5c7fbaf499c665bc73d67c1c87e496da8985131633ee0a95819db2e8
		val app: App,
		val note: String, //optional note
		val note_url: String, //http://optional/note/url
		val updated_at: String, //2011-09-06T20:39:23Z
		val created_at: String, //2011-09-06T17:26:27Z
		val fingerprint: String
)

data class App(
		val url: String, //http://my-github-app.com
		val name: String, //my github app
		val client_id: String //abcde12345fghij67890
)