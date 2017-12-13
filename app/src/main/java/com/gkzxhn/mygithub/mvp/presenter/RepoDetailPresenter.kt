package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Content
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
        (mView as RepoDetailActivity).showStarLoading()
        oAuthApi.starRepo(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    mView.hideStarLoading()
                    if (t.code() == 204) {
                        mView.starred()
                        mView.updateStars(true)
                    }else {
                        mView.unStarred()
                    }
                },{
                    e ->
                    mView.unStarred()
                })
    }

    fun  unStarred(owner: String, repo: String){
        (mView as RepoDetailActivity).showStarLoading()
        oAuthApi.unstarRepo(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    mView.hideStarLoading()
                    if (t.code() == 204) {
                        mView.unStarred()
                        mView.updateStars(false)
                    }else {
                        mView.starred()
                    }
                },{
                    e ->
                    mView.starred()
                })
    }

    fun checkIfWatched(owner: String, repo: String){
        (mView as RepoDetailActivity).showWatchLoading()
        oAuthApi.checkIfWatched(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    mView.hideWatchLoading()
                    if (t.code() == 204) {
                        mView.watched()
                    }else {
                        mView.unwatched()
                    }
                },{
                    e ->
                    mView.unwatched()
                })
    }

    fun watchRepo(owner: String, repo: String) {
        (mView as RepoDetailActivity).showWatchLoading()
        oAuthApi.watchRepo(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    mView.hideWatchLoading()
                    if (t.code() == 204) {
                        mView.watched()
                        mView.updateWatch(true)
                    }else {
                        mView.unwatched()
                    }
                },{
                    e ->
                    mView.unwatched()
                })
    }

    fun  unwatchRepo(owner: String, repo: String){
        (mView as RepoDetailActivity).showWatchLoading()
        oAuthApi.unwatchRepo(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    mView.hideWatchLoading()
                    if (t.code() == 204) {
                        mView.unwatched()
                        mView.updateWatch(false)
                    }else {
                        mView.watched()
                    }
                },{
                    e ->
                    mView.watched()
                })
    }

    fun checkIfStarred(owner: String, repo: String){
        (mView as RepoDetailActivity).showStarLoading()
        oAuthApi.checkIfStarred(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    mView.hideStarLoading()
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

    fun getReadme(owner: String, repo: String){
        (mView as RepoDetailActivity).showReadmeLoading()
        oAuthApi.getReadme(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    content: Content ->
                    mView.hideReadmeLoading()
                    mView.loadReadme(content)
                }, {
                    e: Throwable ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getRepoDetail(owner: String, repo: String) {
        (mView as RepoDetailActivity).showLoading()
        oAuthApi.get(owner, repo)
                .bindToLifecycle(mView)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repo ->
                    mView.hideLoading()
                    mView.initViewByData(repo)

                }, { e ->
                    Log.i(javaClass.simpleName, e.message)
                })

    }
}