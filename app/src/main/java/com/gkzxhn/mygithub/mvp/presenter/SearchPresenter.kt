package com.gkzxhn.mygithub.mvp.presenter

import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.ui.activity.SearchActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/13.
 */
class SearchPresenter @Inject constructor(private val view: BaseView,
                                          private val oAuthApi: OAuthApi) {

    fun searchRepos(string: String){
        oAuthApi.searchRepos(string)
                .bindToLifecycle(view as SearchActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                },{
                    e ->
                })
    }

    fun searchUsers(string : String) {
        oAuthApi.searchUsers(string)
                .bindToLifecycle(view as SearchActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->

                }, {
                    e ->

                })
    }
}