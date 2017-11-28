package com.gkzxhn.mygithub.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.TrendingItem
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.mvp.presenter.RepoListPresenter
import com.gkzxhn.mygithub.ui.activity.RepoDetailActivity
import com.gkzxhn.mygithub.ui.adapter.RepoListAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_repo_list.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/15.
 */
@SuppressLint("ValidFragment")
class RepoListFragment (private val list: List<Repo>) : BaseView, BaseFragment() {

    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var action : String
    private lateinit var loading: LVGhost

    @Inject lateinit var presenter : RepoListPresenter

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initContentView() {
        setReposRecyclerView()
        setData()
    }

    private fun setData() {
        repoListAdapter.setNewData(list)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_repo_list, container, false)
    }

    private fun setReposRecyclerView() {
        rv_repo_list.layoutManager = LinearLayoutManager(context)
        repoListAdapter = RepoListAdapter(null)
        repoListAdapter.openLoadAnimation()
        repoListAdapter.setOnItemClickListener { adapter, view, position ->

            val data = adapter.data[position]
            if (data is Repo) {
                val intent = Intent(context, RepoDetailActivity::class.java)
                val mBundle = Bundle()
                mBundle.putParcelable(IntentConstant.REPO, data)
                intent.putExtras(mBundle)
                startActivity(intent)
            }else if(data is TrendingItem) {

            }
        }
        rv_repo_list.adapter = repoListAdapter
    }
}