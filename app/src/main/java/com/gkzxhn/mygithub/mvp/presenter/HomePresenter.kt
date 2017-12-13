package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.api.TrendingApi
import com.gkzxhn.mygithub.ui.fragment.HomeFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/8.
 */
class HomePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                        private val trendingApi: TrendingApi,
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
        oAuthApi.searchRepos("language:java stars:>1000")
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
}
