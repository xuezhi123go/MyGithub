package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Owner
import com.gkzxhn.mygithub.bean.info.PostIssueResponse
import com.gkzxhn.mygithub.bean.info.SearchUserResult
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
                .subscribe {
                    t ->
                    Log.i(javaClass.simpleName, "when map observer Current thread is " + Thread.currentThread().getName())
                    t.forEachIndexed { index,  owner ->
                    Log.i(javaClass.simpleName, "when map list Current thread is " + Thread.currentThread().getName())
                        if (view.isLoading) {
                            return@subscribe
                        }
                        oAuthApi.getUser(owner.login)
                                .bindToLifecycle(view)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    user: User ->
                                    Log.i(javaClass.simpleName, "get user")
                                    if (!view.isLoading) {
                                        view?.let {
                                            it.updateList(index, user)
                                        }
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
                .subscribe {
                    t ->
                    Log.i(javaClass.simpleName, "when map observer Current thread is " + Thread.currentThread().getName())
                    t.forEachIndexed { index,  owner ->
                        Log.i(javaClass.simpleName, "when map list Current thread is " + Thread.currentThread().getName())
                        if (view.isLoading) {
                            return@subscribe
                        }
                        oAuthApi.getUser(owner.login)
                                .bindToLifecycle(view)
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe({
                                    user: User ->
                                    Log.i(javaClass.simpleName, "get user")
                                    if (!view.isLoading) {
                                        view?.let {
                                            it.hideLoading()
                                            it.updateList(index, user)
                                        }
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
    }

    fun searchUsers(string : String) {
        view.showLoading()
        oAuthApi.searchUsers(string)
                .bindToLifecycle(view as ContributorsFragment)
                .subscribeOn(Schedulers.io())
                .map {
                    t: SearchUserResult ->
                    return@map t.items
                }
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
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe {
                    t ->
                    getUserBio(t, view)
                }
    }

    private fun getUserBio(t: ArrayList<Owner>, view: ContributorsFragment) {
        Log.i(javaClass.simpleName, "when map observer Current thread is " + Thread.currentThread().getName())
        t.forEachIndexed { index, owner ->
            Log.i(javaClass.simpleName, "when map list Current thread is " + Thread.currentThread().getName())
            if (view.isLoading) {
                return
            }
            checkIfFollowIng(index, owner.login)
            oAuthApi.getUser(owner.login)
                    .bindToLifecycle(view)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ user: User ->
                        Log.i(javaClass.simpleName, "get user")
                        if (!view.isLoading) {
                            view?.let {
                                it.hideLoading()
                                it.updateList(index, user)
                            }
                        }
                    },
                            { e ->
                                view?.let {
                                    it.hideLoading()
                                }
                                Log.e(javaClass.simpleName, e.message)
                            })
        }
    }

    /**
     * 检查是否关注该用户
     */
    fun checkIfFollowIng(index: Int, username: String){
        (view as ContributorsFragment).updateListFollowStatus(index, -1)
        oAuthApi.checkIfFollowUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        //已关注
                        view.updateListFollowStatus(index, 0)
                    }else{
                        view.updateListFollowStatus(index, 1)
                    }
                },{
                    e ->
                    Log.i(javaClass.simpleName, e.message)
                })
    }

    /**
     * 关注用户
     */
    fun followUser(index: Int, username: String) {
        (view as ContributorsFragment).updateListFollowStatus(index, -1)
        oAuthApi.followUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(index, 0)
                    }else {
                        view.updateListFollowStatus(index, 1)
                    }
                }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    /**
     * 取消关注用户
     */
    fun unFollowUser(index: Int, username: String) {
        (view as ContributorsFragment).updateListFollowStatus(index, -1)
        oAuthApi.unFollowUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(index, 1)
                    }else {
                        view.updateListFollowStatus(index, 1)
                    }
                }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

}