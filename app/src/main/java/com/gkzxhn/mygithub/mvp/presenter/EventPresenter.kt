package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Event
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.EventFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
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
                .subscribe({ evnet ->
                    if (evnet.size > 0){
                        view.loadData(evnet)
                        Log.i(javaClass.simpleName,"event = "+evnet[2])
                    }else
                    {view.context.toast("没有数据")}
                }, {e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.context.toast("请先登录")})
    }
    fun subscribe() {
        rxBus.toFlowable(Event::class.java)
                .bindToLifecycle(view as EventFragment)
                .subscribe(
                        { event: Event? ->
                            view.getNewData()
                        }
                )
    }
}