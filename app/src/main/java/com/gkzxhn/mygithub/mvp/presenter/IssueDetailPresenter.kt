package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.api.OAuthApi
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.activity.IssueDetailActivity
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.RequestBody
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/26.
 */
class IssueDetailPresenter @Inject constructor(private val oAuthApi: OAuthApi,
                                               private val view: BaseView) {

    private lateinit var username : String
    private lateinit var repoName : String
    private var number : Int = 0

    fun getComments(name: String, repo: String, number: Int) {
        username = name
        repoName = repo
        this.number = number

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

    fun postComment(body: String){
        view.showLoading()
        val requestBody = RequestBody.create(MediaType.parse("application/json"),
                "{\"body\":\"${body}\"}")
        oAuthApi.postIssueComment(username, repoName, number, requestBody)
                .bindToLifecycle(view as IssueDetailActivity)
                .subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    comment ->
                    view.hideLoading()
                    Log.i(javaClass.simpleName, comment.toString())
                    view.addComment(comment)
                },{
                    e ->
                    view.hideLoading()
                    view.toast("提交失败,请稍后重试...")
                    Log.e(javaClass.simpleName, e.message)
                })
    }
}