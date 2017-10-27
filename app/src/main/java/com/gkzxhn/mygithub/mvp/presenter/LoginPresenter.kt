package com.gkzxhn.mygithub.mvp.presenter

import android.content.Intent
import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.AccountApi
import com.gkzxhn.mygithub.api.AuthorRetrofitClient
import com.gkzxhn.mygithub.bean.info.AuthorizationResp
import com.gkzxhn.mygithub.bean.info.CreateAuthorization
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.constant.GithubConstant
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.extension.edit
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/20.
 */

class LoginPresenter @Inject constructor(private val rxBus: RxBus,
                                         private val view: BaseView) {

    val TAG = javaClass.simpleName

    fun login(username: String, password: String) {
        (view as LoginActivity).showLoading()
        val api = AuthorRetrofitClient.getInstance(GithubConstant.BASE_URL)
                .createRetrofit(username, password)!!
                .create(AccountApi::class.java)
        val createAuthorization = CreateAuthorization()
        createAuthorization.note = GithubConstant.NOTE
        createAuthorization.client_id = GithubConstant.CLIENT_ID
        createAuthorization.client_secret = GithubConstant.CLIENT_SECRET
        createAuthorization.scopes = GithubConstant.SCOPES
        api.createAuthorization(createAuthorization)
                .flatMap(Function<AuthorizationResp, Observable<User>> { t: AuthorizationResp ->

                    val accessToken = t.token
                    Log.i(TAG, "accessToken : ${accessToken}")
                    SharedPreConstant.USER_SP
                            .getSharedPreference()
                            .edit {
                                putString(SharedPreConstant.ACCESS_TOKEN, accessToken)
                            }
                    return@Function api.getUserInfo(accessToken)
                })
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ user: User? ->
                    view.hideLoading()
                    val intent = Intent()
                    val avatar_url = user!!.avatar_url
                    val name = user.login
                    intent.putExtra(IntentConstant.AVATAR, avatar_url)
                    intent.putExtra(IntentConstant.NAME, name)

                    SharedPreConstant.USER_SP
                            .getSharedPreference()
                            .edit {
                                putString(SharedPreConstant.USER_NAME, name)
                            }
                    SharedPreConstant.USER_SP
                            .getSharedPreference()
                            .edit {
                                putString(SharedPreConstant.AVATAR_URL, avatar_url)
                            }
//                    (view as LoginActivity).setResult(view.RESULT_OK, intent)
                    rxBus.post(user)
                    view.killMyself()
                    Log.i(TAG, "user: ${user}"
                    )
                }, { e ->
                    view.hideLoading()
                    view.toast("登录失败")
                    Log.e(TAG, "login error : ${e.message}")
                })

//        val createAuthorization = CreateAuthorization()
//        createAuthorization.note = GithubConstant.NOTE
//        createAuthorization.client_id = GithubConstant.CLIENT_ID
//        createAuthorization.client_secret = GithubConstant.CLIENT_SECRET
//        createAuthorization.scopes = GithubConstant.SCOPES
//
//        accountApi.createAuthorization(createAuthorization)
//                .flatMap(Function<AuthorizationResp, Observable<User>> {
//                    t : AuthorizationResp->
//
//                    return@Function accountApi.getUserInfo(t.token)
//                })
//                .subscribeOn(Schedulers.io())
//                .unsubscribeOn(Schedulers.io())
//                .observeOn(AndroidSchedulers.mainThread())
//                .doOnTerminate { view.showLoading() }
//                .doAfterTerminate { view.hideLoading() }
//                .subscribe({
//                    user: User? ->
//                    Log.i(TAG, "user: ${user}"
//                )},{
//                    e -> Log.e(TAG, "login error : ${e.message}")
//                })

    }
}