package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Starred
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.StarsFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/10/25.
 */
class StarsPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                         private val view: BaseView,
                                         private val rxBus: RxBus) {


    fun getStarrde() {
        view.showLoading()
        oAuthApi.getMyStars()
                .bindToLifecycle(view as StarsFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({ stars ->
                    if (stars.size > 0) {
                        view.loadData(stars)
                    }
                }, { e ->
                    Log.e(javaClass.simpleName, e.message)
                    view.context.toast("加载失败...")
                })

    }


    fun subscribe() {
        rxBus.toFlowable(Starred::class.java)
                .bindToLifecycle(view as StarsFragment)
                .subscribe(
                        { stars: Starred? ->
                            view.getNewData()
                        }
                )
    }

}