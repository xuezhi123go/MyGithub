package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.api.TrendingApi
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.ui.activity.RepoListActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/27.
 */
class RepoListPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                            private val trendingApi: TrendingApi,
                                           private val view : BaseView) {

    /**
     * 我的仓库列表
     */
    fun loadRepos(){
        view.showLoading()
        oAuthApi.getRepos(sort = "pushed", direction = "desc")
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: List<Repo>? ->
                    view.hideLoading()
                    view.loadData(t!!)
                    Log.i(javaClass.simpleName, t.get(0).toString())
                },{

                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    /**
     * 组织仓库列表
     */
    fun loadOrgRepos(org :String) {
        view.showLoading()
        oAuthApi.getOrgRepos(org)
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t: List<Repo>? ->
                    view.hideLoading()
                    view.loadData(t!!)
                    Log.i(javaClass.simpleName, t.get(0).toString())
                },{

                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getTrendingRepo(){
        view.showLoading()
        trendingApi.getTrendingRepos(since = "weekly")
                .bindToLifecycle(view as RepoListActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {result ->
                            Log.i(javaClass.simpleName, result.toString())
                            view.hideLoading()
                            view.loadData(result.items)
                        }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }
}