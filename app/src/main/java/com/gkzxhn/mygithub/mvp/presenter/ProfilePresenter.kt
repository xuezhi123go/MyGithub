package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.api.OAuthRetrofitClient
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.ui.fragment.ProfileFragment
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject
import javax.inject.Named

/**
 * Created by 方 on 2017/10/24.
 */
class ProfilePresenter @Inject constructor(@Named("OAuth")private val oAuthRetrofitClient: OAuthRetrofitClient,
                                           private val view : BaseView){

    fun loadData(){
        oAuthRetrofitClient.createRetrofit()!!
                .create(OAuthApi::class.java)
                .getRepos(sort = "pushed", direction = "desc")
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnTerminate { view.showLoading() }
                .doAfterTerminate { view.hideLoading() }
                .subscribe({
                    t: List<Repo>? ->
                    (view as ProfileFragment).loadData(t!!)
                    Log.i(javaClass.simpleName, t.get(0).toString())
                },{
                    e ->
                    Log.e(javaClass.simpleName, e.message)
                })
    }
}