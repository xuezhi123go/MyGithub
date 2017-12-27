package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.ExploreApi
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.api.TrendingApi
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.constant.GithubConstant
import com.gkzxhn.mygithub.extension.sanitizeHtml
import com.gkzxhn.mygithub.ui.fragment.HomeFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/8.
 */
class HomePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                        private val trendingApi: TrendingApi,
                                        private val exploreApi: ExploreApi,
                                        private val mView: BaseView) {

    fun getPopularUser(){
        oAuthApi.searchUsers("followers:>=10000 type:user")
                .bindToLifecycle(mView as HomeFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result ->
                    Log.i(javaClass.simpleName, result.toString())
                            mView.loadPopUsers(result)
                }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getPopularRepos(){
        oAuthApi.searchRepos("stars:>1000")
                .bindToLifecycle(mView as HomeFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result ->
                            mView.loadPopRepos(result)
                            Log.i(javaClass.simpleName, result.toString())
                        }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getTrendingUser(){
        trendingApi.getTrendingUsers(since = "weekly")
                .bindToLifecycle(mView as HomeFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result ->
                            Log.i(javaClass.simpleName, result.toString())
                        }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getTrendingRepo(){
        trendingApi.getTrendingRepos(since = "weekly")
                .bindToLifecycle(mView as HomeFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result ->
                            Log.i(javaClass.simpleName, result.toString())
                            mView.loadRepoWeek(result)
                        }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getBanner() {
        exploreApi.getExploreContent()
                .bindToLifecycle(mView as HomeFragment)
                .sanitizeHtml ({
                    Log.i(javaClass.simpleName, "html : ${this.body().toString()}")
                    val list = parse(this)
                    list
                }).subscribe ({
            list: List<Icon2Name> ->
            mView.setBanner(list)
            list.forEach {
                Log.i(javaClass.simpleName, "src : ${it.name}")
            }
        }, {
            e -> Log.e(javaClass.simpleName, "exceptions : ${e.message}")
        })
    }

    private fun parse(doc: Document): List<Icon2Name> {
        val element = doc.getElementsByClass("carousel-inner").first()
        val list = element
                .children()
                .map { ele ->
                    var child: Element? = null
                    try {
                        child = ele.getElementsByTag("a").first().child(0)
                    } catch (e: Exception) {
                        child = Element("a")
                    }
                    val src = GithubConstant.EXPLORE_URL + child!!.attr("src")
                    val title = child!!.attr("title")
                    Icon2Name(src, title, "banner")
                }.filter {
            it ->
            it.avatarUrl != GithubConstant.EXPLORE_URL
        }
        return list
    }
}
