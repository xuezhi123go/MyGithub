package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.Content
import com.gkzxhn.mygithub.extension.base64Decode
import com.gkzxhn.mygithub.ui.activity.FileDetailActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/10/25.
 */
class FileDetailPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                              private val view: BaseView) {


    fun getCode(login: String, name: String, path: String) {
        (view as FileDetailActivity).showLoading()
        oAuthApi.getContentDetail(login, name, path, null)
                .bindToLifecycle(view)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .map {
                    content: Content ->
                    content.content.base64Decode()
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ result ->
                    view.hideLoading()
                    view.loadData(result)
                    Log.i(javaClass.simpleName, "getCode : $result")
                }, { e ->
                    Log.i(javaClass.simpleName, e.message)
                })
    }

}