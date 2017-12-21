package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.ui.fragment.FileListFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.gkzxhn.mygithub.utils.rxbus.entity.NextPageEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/12/11.
 */
class FileListPresenter @Inject constructor(private val rxBus: RxBus,
                                            private val oAuthApi: OAuthApi,
                                            private val view: BaseView) {

    fun getCode(login: String, name: String, path: String) {
        (view as FileListFragment).showLoading()
        oAuthApi.getCodeDetail(login, name, path, null)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    view.hideLoading()
                    view.loadData(result)
                    Log.i(javaClass.simpleName, "getCode : $result")
                }, { e ->
                    Log.i(javaClass.simpleName, e.message)
                })
    }

    fun goNextPage(position: Int, path: String, name: String) {
        rxBus.post(NextPageEvent(position, path, name))
    }
}