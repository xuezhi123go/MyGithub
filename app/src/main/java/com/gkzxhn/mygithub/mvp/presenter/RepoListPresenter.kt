package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.api.TrendingApi
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.activity.RepoListActivity
import com.google.gson.Gson
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/27.
 */
class RepoListPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                            private val trendingApi: TrendingApi,
                                            private val view: BaseView) {

    /**
     * 我的仓库列表
     */
    fun loadRepos() {
        view.showLoading()
        oAuthApi.getRepos(sort = "pushed", direction = "desc")
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: List<Repo>? ->
                    view.hideLoading()
                    view.loadData(t!!)
                    Log.i(javaClass.simpleName, t.get(0).toString())
                }, {

                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    /**
     * 组织仓库列表
     */
    fun loadOrgRepos(org: String) {
        view.showLoading()
        oAuthApi.getOrgRepos(org)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t: List<Repo>? ->
                    view.hideLoading()
                    view.loadData(t!!)
                    Log.i(javaClass.simpleName, t.get(0).toString())
                }, {

                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getTrendingRepo() {
        view.showLoading()
        trendingApi.getTrendingRepos(since = "weekly")
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.i(javaClass.simpleName, result.toString())
                            view.hideLoading()
                            view.loadData(result.items)
                        }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.hideLoading()
                })
    }

    fun getPopularUser() {
        view.showLoading()
        oAuthApi.searchUsers("followers:>=10000 type:user")
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.i(javaClass.simpleName, result.toString())
                            view.hideLoading()
                            val list = result.items
                                    .map { item ->
                                        return@map Icon2Name(item.avatar_url, item.login, "user")
                                    }
                            view.loadPopUsers(list)
                            getUserBio(list, view)
                        }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getUserBio(t: List<Icon2Name>, view: RepoListActivity) {
        Log.i(javaClass.simpleName, "when map observer Current thread is " + Thread.currentThread().getName())
        t.forEachIndexed { index, owner ->
            Log.i(javaClass.simpleName, "when map list Current thread is " + Thread.currentThread().getName())
            if (!view.isOn) {
                return
            }
            checkIfFollowIng(index, owner.name)
            oAuthApi.getUser(owner.name)
                    .bindToLifecycle(view)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user: User ->
                        Log.i(javaClass.simpleName, "get user")
                        if (view.isOn) {
                            view?.let {
                                it.updateList(index, user)
                            }
                        }
                    },
                            { e ->
                                view?.let {
                                }
                                Log.e(javaClass.simpleName, e.message)
                            })
        }
    }

    fun getPopularRepos() {
        oAuthApi.searchRepos("language:java stars:>1000")
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            view.loadData(result.items)
                            Log.i(javaClass.simpleName, result.toString())
                        }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getUserFollowers(username: String) {
        view.showLoading()
        oAuthApi.getUserFollowers(username)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.i(javaClass.simpleName, result.toString())
                            view.hideLoading()
                            view.loadUsers(result)
                            val list = result
                                    .map { item ->
                                        return@map Icon2Name(item.avatar_url, item.login, "user")
                                    }
                            getUserBio(list, view)
                        }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getUserOrgs(username: String) {
        view.showLoading()
        oAuthApi.getUserOrgs(username)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.i(javaClass.simpleName, result.toString())
                            view.hideLoading()
                            view.loadOrgs(result)
                        }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getUserFollowing(username: String) {
        view.showLoading()
        oAuthApi.getUserFollowing(username)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result ->
                            Log.i(javaClass.simpleName, result.toString())
                            view.hideLoading()
                            view.loadUsers(result)
                            val list = result
                                    .map { item ->
                                        return@map Icon2Name(item.avatar_url, item.login, "user")
                                    }
                            getUserBio(list, view)
                        }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun loadUserRepos(username: String) {
        view.showLoading()
        oAuthApi.getUserRepos(username)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ datas ->
                    view.loadData(datas)
                    view.hideLoading()
                }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.hideLoading()
                })
    }

    fun getStaredRepos(username: String) {
        view.showLoading()
        oAuthApi.getStars(username)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ datas ->
                    view.loadData(datas)
                    view.hideLoading()
                }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.hideLoading()
                })
    }

    /**
     * 检查是否关注该用户
     */
    fun checkIfFollowIng(index: Int, username: String) {
        (view as RepoListActivity).updateListFollowStatus(index, -1)
        oAuthApi.checkIfFollowUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        //已关注
                        view.updateListFollowStatus(index, 0)
                    } else {
                        view.updateListFollowStatus(index, 1)
                    }
                }, { e ->
                    Log.i(javaClass.simpleName, e.message)
                })
    }

    /**
     * 关注用户
     */
    fun followUser(index: Int, username: String) {
        (view as RepoListActivity).updateListFollowStatus(index, -1)
        oAuthApi.followUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(index, 0)
                    } else {
                        view.updateListFollowStatus(index, 1)
                    }
                }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    /**
     * 取消关注用户
     */
    fun unFollowUser(index: Int, username: String) {
        (view as RepoListActivity).updateListFollowStatus(index, -1)
        oAuthApi.unFollowUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(index, 1)
                    } else {
                        view.updateListFollowStatus(index, 1)
                    }
                }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    /**
     * 获取用户Public Activity
     */
    fun getPublicActivity(username: String) {
        view.showLoading()
        oAuthApi.getEventThatAUserPublicPerformed(username)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({event->
                    if (event.size > 0) {
                        view.loadActivityData(event)
                        Log.i(javaClass.simpleName, "event = " + Gson().toJson(event))
                    } else {
                        view.toast("没有数据")
                    }
                }, { e ->
                    view.toast("网络错误")
                    Log.e(javaClass.simpleName, "e = " + e)
                })
    }
}