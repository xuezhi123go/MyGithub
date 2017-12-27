package com.gkzxhn.mygithub.extension

import com.gkzxhn.mygithub.constant.GithubConstant
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.ResponseBody
import org.jsoup.Jsoup
import org.jsoup.nodes.Document

/**
 * Created by 方 on 2017/12/22.
 */

fun <T> Observable<ResponseBody>.sanitizeHtml(transform: Document.() ->  T): Observable<T> = this
        .subscribeOn(Schedulers.io())
        .retryWhen(GithubConstant.RetryWithDelay())
        .map {
            response ->
//            Log.i(javaClass.simpleName, "response: ${response.string().trim()} ")
            val document = Jsoup.parse(response.string())
//            Log.i(javaClass.simpleName, document.data())
            document.transform()
        }
        .observeOn(AndroidSchedulers.mainThread())