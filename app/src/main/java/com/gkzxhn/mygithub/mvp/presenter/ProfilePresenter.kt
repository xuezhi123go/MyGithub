package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.ui.activity.UserActivity
import com.gkzxhn.mygithub.ui.fragment.ProfileFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/24.
 */
class ProfilePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                           private val view : BaseView,
                                           private val rxBus: RxBus){

    fun loadUserData(username: String){
        view.showLoading()
        oAuthApi.getOrg("GKZX-HN")
                .bindToLifecycle(view as ProfileFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    view.hideLoading()
                    val datas = arrayListOf<Organization>()
                    datas.add(t)
                    view.loadData(datas!!)
                    Log.i(javaClass.simpleName, t.toString())
                },{
                    e->
                    view.hideLoading()
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun subscribe(){
        rxBus.toFlowable(User::class.java)
                .bindToLifecycle(view as ProfileFragment)
                .subscribe(
                        {user: User? ->
                            view.getNewData()
                        }
                )
    }

    fun getUser(username: String){
        view.showLoading()
        oAuthApi.getUser(username)
                .bindToLifecycle(view as UserActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    user ->
                    view.hideLoading()
                    view.loadData(user)
                    Log.i(javaClass.simpleName, user.toString())
                },{
                    e->
                    view.hideLoading()
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun loadUserRepos(username: String){
        view.showLoading()
        oAuthApi.getUserRepos(username)
                .bindToLifecycle(view as UserActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    datas ->
                    view.loadRepos(datas)
                    view.hideLoading()
                },{
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.hideLoading()
                })
    }
}