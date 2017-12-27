package com.gkzxhn.mygithub.constant

import io.reactivex.Observable
import io.reactivex.functions.Function
import java.util.concurrent.TimeUnit

/**
 * Created by 方 on 2017/10/20.
 */
object GithubConstant {
    // client id/secret
    val CLIENT_ID = "255783b8735126fd2c6d"
    val CLIENT_SECRET = "18beb7c379545fd32b6f32c944112b289167f84b"

    // scopes
    val SCOPES = arrayOf("user", "repo", "notifications", "gist", /*"read:org", */"admin:org")

    val NOTE = "MyGithub"

    val AUTHOR_NAME = "FangforFun"
    val BASE_URL = "https://api.github.com/"
    val Trending_URL = "https://trendings.herokuapp.com/"

    //轮播图主页
    val EXPLORE_URL = "http://www.jcodecraeer.com/"

    class RetryWithDelay(val maxRetries: Int = 3, val delayMillis: Long = 2000L) : Function<Observable<in Throwable>, Observable<*>> {
        var retryCount = 0

        override fun apply(observable: Observable<in Throwable>): Observable<*> = observable
                .flatMap { throwable ->
                    if (++retryCount < maxRetries) {
                        Observable.timer(delayMillis, TimeUnit.MILLISECONDS)
                    } else {
                        Observable.error(throwable as Throwable)
                    }
                }
    }
}