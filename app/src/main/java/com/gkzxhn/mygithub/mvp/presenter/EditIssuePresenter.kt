package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.PostIssue
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.activity.EditIssueActivity
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.google.gson.Gson
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/27.
 */
class EditIssuePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                             private val view : BaseView,
                                             private val gson: Gson,
                                             private val rxBus: RxBus) {

    fun postIssue(username: String,
                  repoName: String,
                  title: String,
                  body: String) {
        view.showLoading()
        val requestBody = RequestBody.create(MediaType.parse("application/json"),
                gson.toJson(PostIssue(title, body, null, null, null)))
        oAuthApi.postIssue(username, repoName, requestBody)
                .bindToLifecycle(view as EditIssueActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    response ->
                    view.hideLoading()
                    rxBus.post(response)
                    Log.i(javaClass.simpleName, response.toString())
                    view.finish()
                },{
                    e ->
                    view.hideLoading()
                    view.toast("提交失败,请稍后重试...")
                    Log.e(javaClass.simpleName, e.message)
                })
    }
}