package com.gkzxhn.mygithub.ui.fragment

import android.annotation.SuppressLint
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
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.IssuePresenter
import com.gkzxhn.mygithub.ui.activity.IssueDetailActivity
import com.gkzxhn.mygithub.ui.adapter.IssueAdapter
import kotlinx.android.synthetic.main.fragment_issue.*
import javax.inject.Inject

@SuppressLint("ValidFragment")
/**
 * Created by 方 on 2017/10/25.
 */
class IssueFragment (private val repo: Repo, val content_tag: String) : BaseFragment(), BaseView {

    @Inject lateinit var presenter: IssuePresenter
    private lateinit var adapter: IssueAdapter

    lateinit var repoName : String
    lateinit var owner : String

    override fun launchActivity(intent: Intent) {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        srl_issue?.let {
            it.isRefreshing = true
        }
    }

    override fun hideLoading() {
        srl_issue?.let {
            it.isRefreshing = false
        }
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    override fun initContentView() {

        srl_issue.setOnRefreshListener {
            getNewData()
        }

        adapter = IssueAdapter(null)
        adapter.openLoadAnimation()
        rv_issue.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener { adapter, view, position ->
            val issue = adapter.data[position] as Issue
            val name = owner
            val repo = repoName
            val number = issue.number
            val time = issue.created_at
            val avatar = issue.user.avatar_url
            val body = issue.body
            val title = issue.title
            val intent = Intent(context, IssueDetailActivity::class.java)
            intent.putExtra(IntentConstant.TYPE, content_tag)
            intent.putExtra(IntentConstant.NAME, name)
            intent.putExtra(IntentConstant.REPO, repo)
            intent.putExtra(IntentConstant.ISSUE_NUM, number)
            intent.putExtra(IntentConstant.CREATE_TIME, time)
            intent.putExtra(IntentConstant.AVATAR, avatar)
            intent.putExtra(IntentConstant.BODY, body)
            intent.putExtra(IntentConstant.TITLE, title)
            startActivity(intent)
        }
        rv_issue.adapter = adapter

        presenter.addSubscribe()
        getNewData()
    }

    private fun getNewData() {
        owner = repo.owner.login
        repoName = repo.name

        when (content_tag) {
            IntentConstant.OPEN -> {
                presenter.getOpenIssues(owner, repoName)
            }
            IntentConstant.CLOSED -> {
                presenter.getClosedIssues(owner, repoName)
            }
            else -> {
                presenter.getIssues(owner, repoName)
            }
        }
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

    fun loadData(issues: List<Issue>) {
        Log.i(javaClass.simpleName, issues[0].toString())
        if (issues.size == 0) {
            context.toast("空空如也~")
            adapter.setEmptyView(LayoutInflater.from(context).inflate(R.layout.empty_view, null, false))
        }
        adapter.setNewData(issues)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(javaClass.simpleName, "ondestroy")
    }
}