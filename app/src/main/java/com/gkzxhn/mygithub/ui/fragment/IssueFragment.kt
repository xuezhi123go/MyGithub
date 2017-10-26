package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Issue
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.mvp.presenter.IssuePresenter
import com.gkzxhn.mygithub.ui.adapter.IssueAdapter
import kotlinx.android.synthetic.main.fragment_issue.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/25.
 */
class IssueFragment constructor(private val repo: Repo): BaseFragment(), BaseView {

    @Inject lateinit var presenter: IssuePresenter
    private lateinit var adapter : IssueAdapter

    override fun launchActivity(intent: Intent) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    override fun initContentView() {
        adapter = IssueAdapter(null)
        rv_issue.layoutManager = LinearLayoutManager(context)
        rv_issue.adapter = adapter

        val owner = repo.owner.login
        val repoName = repo.name
        presenter.getIssues(owner, repoName)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_issue, container, false)
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    fun loadData(issues : List<Issue>){
        Log.i(javaClass.simpleName, issues[0].toString())
        adapter.setNewData(issues)
    }
}