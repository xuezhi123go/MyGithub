package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.activity.IssueDetailActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/26.
 */
class IssueDetailPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                               private val view: BaseView) {

    fun getComments(name: String, repo: String, number: Int) {
        view.showLoading()
        oAuthApi.getComments(name, repo, number)
                .bindToLifecycle(view as IssueDetailActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        {
                            datas ->
                            view.hideLoading()
                            view.loadData(datas)
                        },
                        {
                            e ->
                            view.hideLoading()
                            view.toast("加载评论失败")
                            Log.e(javaClass.simpleName, e.message)
                        }
                )
    }
}