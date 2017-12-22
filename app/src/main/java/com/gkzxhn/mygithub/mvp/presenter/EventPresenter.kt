package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import android.view.View
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.EventFragment
import com.gkzxhn.mygithub.utils.SPUtil
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.fragment_notifications.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/11/29.
 */
class EventPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                         private val view: BaseView,
                                         private val rxBus: RxBus) {

    fun getEvents(username: String) {
        view.showLoading()
        oAuthApi.getEventsThatAUserHasReceived(username)
                .bindToLifecycle(view as EventFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ event ->
                    if (event.size > 0) {
                        view.tv_notifications_login.visibility = View.GONE
                        view.srl_notifications.visibility = View.VISIBLE
                        view.loadData(event)
                        if (!event.toString().equals(SPUtil.get(view.context, "event", ""))) {
                            SPUtil.put(view.context, "event", event.toString())
                        }
                        Log.i(javaClass.simpleName, "event12 = " + event[12])
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

    fun subscribe() {
        rxBus.toFlowable(User::class.java)
                .bindToLifecycle(view as EventFragment)
                .subscribe(
                        { user: User? ->
                            view.getNewData()
                        }
                )

    }

    fun getRepoDetail(owner: String, repo: String) {
        view.showLoading()
        oAuthApi.get(owner, repo)
                .bindToLifecycle(view as EventFragment)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repo ->
                    //view.hideLoading()
                    view.toIssues(repo)
                }, { e ->
                    Log.i(javaClass.simpleName, e.message)
                })

    }

    fun getEvents(){
        oAuthApi.getEvents()
                .bindToLifecycle(view as EventFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ event ->
                    Log.i(javaClass.simpleName, "event = $event")
                }, { e ->
                    Log.e(javaClass.simpleName, "e = " + e.message)
                    view.context.toast("请先登录")
                    view.tv_notifications_login.visibility = View.VISIBLE
                    view.srl_notifications.visibility = View.GONE
                })
    }
}