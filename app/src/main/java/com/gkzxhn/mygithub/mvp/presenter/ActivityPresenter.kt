package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import android.view.View
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.ActivityFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.google.gson.Gson
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_notifications.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/12/14.
 */
class ActivityPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                            private val view: BaseView,
                                            private val rxBus: RxBus) {


    fun getEvents(username: String) {
        view.showLoading()
        oAuthApi.getEventsThatAUserPerformed(username)
                .bindToLifecycle(view as ActivityFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ event ->
                    if (event.size > 0) {
                        view.tv_notifications_login.visibility = View.GONE
                        view.srl_notifications.visibility = View.VISIBLE
                        view.loadData(event)
                        Log.i(javaClass.simpleName, "event = " + Gson().toJson(event))
                        //LogUtil.i(javaClass.simpleName,"event = " + Gson().toJson(event))
                    } else {
                        view.context.toast("没有数据")
                    }
                }, { e ->
                    Log.e(javaClass.simpleName, "e = " + e.message)
                    view.context.toast("请先登录")
                    view.tv_notifications_login.visibility = View.VISIBLE
                    view.srl_notifications.visibility = View.GONE
                })
    }

    fun getRepoDetail(owner: String, repo: String) {

        oAuthApi.get(owner, repo)
                .bindToLifecycle(view as ActivityFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repo ->

                    view.toRepoDetailActivity(repo)

                }, { e ->
                    Log.i(javaClass.simpleName, e.message)
                })

    }
}