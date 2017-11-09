package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Owner
import com.gkzxhn.mygithub.bean.info.PostIssueResponse
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.ContributorsFragment
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/25.
 */
class IssuePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                         private val view: BaseView,
                                         private val rxBus: RxBus) {


    private var owner :String? = null
    private var repo : String? = null

    fun getIssues(owner : String, repo: String){
        this.owner = owner
        this.repo = repo
        view.showLoading()
        oAuthApi.getIssues(owner = owner, repo = repo)
                .bindToLifecycle(view as IssueFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    issues ->
                    if (issues.size > 0) {
                        view.loadData(issues)
                    }
                    view.hideLoading()
                },{
                    e ->
                    Log.e(javaClass.simpleName, e.message )
                    view.context.toast("加载失败...")
                })
    }

    fun addSubscribe(){
        rxBus.toFlowable(PostIssueResponse::class.java)
                .bindToLifecycle(view as IssueFragment)
                .subscribe(
                        {
                            t -> getIssues(owner = owner!!, repo = repo!!)
                        },
                        {
                            e ->
                            Log.e(javaClass.simpleName, e.message)
                        }
                )

    }

    fun getContributors(owner : String, repo: String){
        this.owner = owner
        this.repo = repo
        view.showLoading()
        oAuthApi.contributors(owner, repo)
                .bindToLifecycle(view as ContributorsFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    lists: List<Owner> ->
                    view?.let {
                        it.hideLoading()
                        it.loadData(lists)
                    }
                }
                .doOnError {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                        view?.let { it.hideLoading()
                    if (e is NullPointerException)
                        //空仓库
                        else
                        it.context.toast("加载失败")
                    }
                }
                .onErrorReturn {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                    val list = arrayListOf<Owner>()
                    view.loadData(list)
                    list
                }
                .observeOn(Schedulers.io())
                .forEach {
                    t ->
                    Log.i(javaClass.simpleName, "when map observer Current thread is " + Thread.currentThread().getName())
                    t.forEachIndexed { index,  owner ->
                    Log.i(javaClass.simpleName, "when map list Current thread is " + Thread.currentThread().getName())
                        oAuthApi.getUser(owner.login)
                                .bindToLifecycle(view)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    user: User ->
                                    Log.i(javaClass.simpleName, "get user")
                                    view?.let {
                                        it.hideLoading()
//                                        it.loadData(user)
                                        it.updateList(index, user)
                                    }
                                },
                                        {
                                            e ->
                                            view?.let {
                                                it.hideLoading()
                                            }
                                            Log.e(javaClass.simpleName, e.message)
                                        })
                    }
                }
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(
//                        {
//                            lists: List<User> ->
//                            Log.i(javaClass.simpleName, "get user list")
//                            view?.let {
//                                it.hideLoading()
//                                it.loadData(lists)
//                            }
//                        },
//                        {
//                            e ->
//                            view?.let {
//                                it.hideLoading()
//                            }
//                            Log.e(javaClass.simpleName, e.message)
//                        }
//                )
    }

    fun getForks(owner : String, repo: String){
        this.owner = owner
        this.repo = repo
        view.showLoading()
        oAuthApi.listForks(owner, repo, "newest")
                .map {
                    repos ->
                    repos.map {
                        repo ->
                        repo.owner
                    }
                }
                .bindToLifecycle(view as ContributorsFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnNext {
                    owners: List<Owner> ->
                    view?.let {
                        it.hideLoading()
                        it.loadData(owners)
                    }
                }
                .doOnError {
                    e ->
                    view?.let {
                        it.context.toast("加载失败")
                        it.hideLoading()
                    }
                    Log.e(javaClass.simpleName, e.message)
                }
                .observeOn(Schedulers.io())
                .map {
                    t ->
                    Log.i(javaClass.simpleName, "when map Current thread is " + Thread.currentThread().getName());
                    t.map {
                        owner ->
                        oAuthApi.getUser(owner.login)
                                .blockingFirst()
                    }
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            users ->
                            view?.let {
                                it.loadData(users)
                            }
                        },
                        {
                            e ->
                            view?.let {
                                it.hideLoading()
                            }
                            Log.e(javaClass.simpleName, e.message)
                        }
                )
    }
}