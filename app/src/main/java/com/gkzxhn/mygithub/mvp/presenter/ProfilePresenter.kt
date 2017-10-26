package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.User
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

    fun loadData(){
        view.showLoading()
        oAuthApi.getRepos(sort = "pushed", direction = "desc")
                .bindToLifecycle(view as ProfileFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({
                    t: List<Repo>? ->
                    view.loadData(t!!)
                    Log.i(javaClass.simpleName, t.get(0).toString())
                },{
                    e ->
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
}