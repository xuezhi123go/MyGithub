package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.ui.activity.UserActivity
import com.gkzxhn.mygithub.ui.fragment.ProfileFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.google.gson.Gson
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.lang.Exception
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/24.
 */
class ProfilePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                           private val view: BaseView,
                                           private val gson: Gson,
                                           private val rxBus: RxBus) {

    fun loadUserData(username: String) {
        view.showLoading()
        oAuthApi.getUserOrgs(username)
                .bindToLifecycle(view as ProfileFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    view.hideLoading()
                    val datas = arrayListOf<Organization>()
                    datas.addAll(t)
                    view.loadData(datas!!)
                    Log.i(javaClass.simpleName, t.toString())
                }, { e ->
                    view.hideLoading()
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getOrg(orgName: String) {
        view.showLoading()
        oAuthApi.getOrg(orgName)
                .bindToLifecycle(view as UserActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ t ->
                    view.hideLoading()

                    view.loadData(t)
                    Log.i(javaClass.simpleName, t.toString())
                }, { e ->
                    view.hideLoading()
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun subscribe() {
        rxBus.toFlowable(User::class.java)
                .bindToLifecycle(view as ProfileFragment)
                .subscribe(
                        { user: User? ->
                            view.getNewData()
                        }
                )
    }

    fun getUser(username: String) {
        view.showLoading()
        oAuthApi.getUser(username)
                .bindToLifecycle(view as UserActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user ->
                    view.hideLoading()
                    view.loadData(user)
                    Log.i(javaClass.simpleName, user.toString())
                }, { e ->
                    view.hideLoading()
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    fun getLocalUser(): User? {
        try {
            return  gson.fromJson(SharedPreConstant.USER_SP
                    .getSharedPreference()
                    .getString(SharedPreConstant.USER_JSON, ""), User::class.java)
        } catch (e: Exception) {
            Log.e(javaClass.simpleName, e.message)
            return null
        }
    }


    /**
     * 检查是否关注该用户
     */
    fun checkIfFollowIng(username: String){
        (view as UserActivity).updateListFollowStatus(-1)
        oAuthApi.checkIfFollowUser(username)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    //TODO
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(0)
                    }else{
                        view.updateListFollowStatus(1)
                    }
                },{
                    e ->
                    Log.i(javaClass.simpleName, e.message)
                })
    }

    /**
     * 关注用户
     */
    fun followUser(username: String) {
        (view as UserActivity).updateListFollowStatus(-1)
        oAuthApi.followUser(username)
                .bindToLifecycle(view as UserActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    //TODO
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(0)
                    }else {
                        view.updateListFollowStatus(1)
                    }
                }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }

    /**
     * 取消关注用户
     */
    fun unFollowUser(username: String) {
        (view as UserActivity).updateListFollowStatus(-1)
        oAuthApi.unFollowUser(username)
                .bindToLifecycle(view as UserActivity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    t ->
                    //TODO
                    Log.i(javaClass.simpleName, t.message())
                    if (t.code() == 204) {
                        view.updateListFollowStatus(1)

                    }else {

                        view.updateListFollowStatus(0)
                    }
                }, {
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }
}