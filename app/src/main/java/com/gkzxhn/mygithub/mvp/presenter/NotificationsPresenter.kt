package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Notifications
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.NotificationsFragment
import com.gkzxhn.mygithub.utils.Utils
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/11/16.
 */
class NotificationsPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                                 private val view: BaseView,
                                                 private val rxBus: RxBus) {
    fun getNotifications(
            //owner:String,repo:String
    ) {
        view.showLoading()
        Log.e(javaClass.simpleName, "showLoading")
        oAuthApi//.getNotificationsInRepository(owner, repo,
                .getNotifications(true, false,
                        since = Utils.getFormatTime(Utils.getDateBeforeOneMonth(-1)!!)!!,
                        before = Utils.getFormatTime(Utils.getTiem())!!)
                .bindToLifecycle(view as NotificationsFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ notifications ->

                    if (notifications.size > 0) {

                        Log.i(javaClass.simpleName, "notifications = " + notifications.toString())

                        view.loadData(notifications)

                        //view.loadData()
                    } else {
                        Log.i(javaClass.simpleName, "notificationgs = " + notifications.toString())
                        Log.i(javaClass.simpleName, "没有notifications数据")
                        view.context.toast("没有消息数据")
                    }
                }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.context.toast("网络错误")
                })
    }

    fun subscribe() {
        rxBus.toFlowable(Notifications::class.java)
                .bindToLifecycle(view as NotificationsFragment)
                .subscribe(
                        { notifications: Notifications? ->
                            view.getNewData()
                        }
                )
    }

}