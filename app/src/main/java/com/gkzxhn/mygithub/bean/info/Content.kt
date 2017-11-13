package com.gkzxhn.mygithub.bean.info

/**
 * Created by 方 on 2017/11/10.
 */


data class Content(
		val type: String, //file
		val encoding: String, //base64
		val size: Int, //5362
		val name: String, //README.md
		val path: String, //README.md
		val content: String, //encoded content ...
		val sha: String, //3d21ec53a331a6f037a91c368710b99387d012c1
		val url: String, //https://api.github.com/repos/octokit/octokit.rb/contents/README.md
		val git_url: String, //https://api.github.com/repos/octokit/octokit.rb/git/blobs/3d21ec53a331a6f037a91c368710b99387d012c1
		val html_url: String, //https://github.com/octokit/octokit.rb/blob/master/README.md
		val download_url: String, //https://raw.githubusercontent.com/octokit/octokit.rb/master/README.md
		val _links: Links
)

data class Links(
		val git: String, //https://api.github.com/repos/octokit/octokit.rb/git/blobs/3d21ec53a331a6f037a91c368710b99387d012c1
		val self: String, //https://api.github.com/repos/octokit/octokit.rb/contents/README.md
		val html: String //https://github.com/octokit/octokit.rb/blob/master/README.md
)