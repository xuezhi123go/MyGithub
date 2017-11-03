package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.mvp.presenter.IssuePresenter
import com.gkzxhn.mygithub.ui.activity.UserActivity
import com.gkzxhn.mygithub.ui.adapter.UserListAdapter
import com.gkzxhn.mygithub.ui.wedgit.SpaceItemDecoration
import kotlinx.android.synthetic.main.fragment_issue.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/2.
 */
class ContributorsFragment constructor(private val repo: Repo,
                                       private val type : String) : BaseView, BaseFragment() {

    @Inject lateinit var presenter:IssuePresenter

    private lateinit var owner : String
    private lateinit var repoName: String

    private lateinit var adapter: UserListAdapter

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initContentView() {

        initRecyclerView()

        getNewData()
    }

    private fun initRecyclerView() {
        adapter = UserListAdapter(null)
        adapter.openLoadAnimation()
        rv_issue.layoutManager = LinearLayoutManager(context)
        rv_issue.adapter = adapter
        rv_issue.addItemDecoration(SpaceItemDecoration(2f, adapter.data.size))
        adapter.setOnItemClickListener {
            adapter, view, position ->
            val user = adapter.data[position] as Parcelable
            val intent = Intent(context, UserActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(IntentConstant.User, user)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        srl_issue.setOnRefreshListener {
            getNewData()
        }
    }

    private fun getNewData() {
        owner = repo.owner.login
        repoName = repo.name
        if (type.equals(IntentConstant.CONTRIBUTORS)) {
            presenter.getContributors(owner, repoName)
        }else if(type.equals(IntentConstant.FORKS)) {
            presenter.getForks(owner, repoName)
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_issue, container, false)
    }

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun loadData(lists: List<Any>){
        if (lists.size == 0) {
            adapter.setEmptyView(LayoutInflater.from(context).inflate(R.layout.empty_view, null, false))
        }
        adapter.setNewData(lists)

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(javaClass.simpleName, "ondestroy")
    }
}