package com.gkzxhn.mygithub.mvp.presenter

import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.ui.activity.RepoDetailActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/10.
 */
class RepoDetailPresenter @Inject constructor(private val mView : BaseView,
                                              private val oAuthApi: OAuthApi) {

    fun starRepo(owner: String, repo: String) {
        oAuthApi.starRepo(owner, repo)
                .bindToLifecycle(mView as RepoDetailActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    if (t.code() == 204) {
                        mView.starred()
                    }else {
                        mView.unStarred()
                    }
                },{
                    e ->
                    mView.unStarred()
                })
    }

    fun  unStarred(owner: String, repo: String){
        oAuthApi.unstarRepo(owner, repo)
                .bindToLifecycle(mView as RepoDetailActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    if (t.code() == 204) {
                        mView.unStarred()
                    }else {
                        mView.starred()
                    }
                },{
                    e ->
                    mView.starred()
                })
    }

    fun checkIfStarred(owner: String, repo: String){
        oAuthApi.checkIfStarred(owner, repo)
                .bindToLifecycle(mView as RepoDetailActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    if (t.code() == 204) {
                        mView.starred()
                    }else {
                        mView.unStarred()
                    }
                },{
                    e ->
                    mView.unStarred()
                })
    }
}