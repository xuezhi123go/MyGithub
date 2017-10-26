package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/25.
 */
class IssuePresenter @Inject constructor(private val oAuthApi: OAuthApi, private val view: BaseView) {

    fun getIssues(owner : String, repo: String){
        view.showLoading()
        oAuthApi.getIssues(owner = owner, repo = repo)
                .bindToLifecycle(view as IssueFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doAfterTerminate { view.hideLoading() }
                .subscribe({
                    issues ->

                    if (issues.size > 0) {
                        view.loadData(issues)
                    }
                },{
                    e ->
                    Log.e(javaClass.simpleName, e.message )
                    view.context.toast("加载失败...")
                })
    }
}