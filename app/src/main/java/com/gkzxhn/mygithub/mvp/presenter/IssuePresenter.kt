package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.bean.info.PostIssueResponse
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/25.
 */
class IssuePresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                         private val view: BaseView,
                                         private val rxBus: RxBus) {


    private var owner :String? = null
    private var repo : String? = null

    fun getIssues(owner : String, repo: String){
        this.owner = owner
        this.repo = repo
        view.showLoading()
        oAuthApi.getIssues(owner = owner, repo = repo)
                .bindToLifecycle(view as IssueFragment)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    issues ->
                    if (issues.size > 0) {
                        view.loadData(issues)
                    }
                    view.hideLoading()
                },{
                    e ->
                    Log.e(javaClass.simpleName, e.message )
                    view.context.toast("加载失败...")
                })
    }

    fun addSubscribe(){
        rxBus.toFlowable(PostIssueResponse::class.java)
                .bindToLifecycle(view as IssueFragment)
                .subscribe(
                        {
                            t -> getIssues(owner = owner!!, repo = repo!!)
                        },
                        {
                            e ->
                            Log.e(javaClass.simpleName, e.message)
                        }
                )

    }
}