package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.TrendingItem
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.mvp.presenter.RepoListPresenter
import com.gkzxhn.mygithub.ui.adapter.RepoListAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.activity_repo_list.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/27.
 */
class RepoListActivity :BaseActivity(), BaseView {

    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var action : String
    private lateinit var loading: LVGhost

    @Inject lateinit var presenter : RepoListPresenter

    override fun launchActivity(intent: Intent) {

    }

    override fun showLoading() {
        loading = LVGhost(this)
        val params = LinearLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt())
        params.topMargin = 200f.dp2px().toInt()
        params.leftMargin = 30f.dp2px().toInt()
        loading.layoutParams = params
        loading.startAnim()
        ll_repo_list.addView(loading, 2)
    }

    override fun hideLoading() {
        loading.stopAnim()
        ll_repo_list.removeView(loading)
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_repo_list)
        setToolBar()
        setReposRecyclerView()


        action = intent.action
        when(action) {
            IntentConstant.MY_REPOS -> {
                presenter.loadRepos()
                toolbar.title = SharedPreConstant.USER_SP.getSharedPreference()
                        .getString(SharedPreConstant.USER_NAME, "")
            }
            IntentConstant.ORG_REPOS -> {
                val org = intent.getStringExtra(IntentConstant.ORG_NAME)
                toolbar.title = org
                presenter.loadOrgRepos(org)
            }
            IntentConstant.TRENDING_REPO -> {
                val list = intent.getParcelableArrayListExtra<TrendingItem>(IntentConstant.REPO_ENTITIES)
                if (list.size > 0) {
                    repoListAdapter.setNewData(list as List<Parcelable>?)
                }else {
                    presenter.getTrendingRepo()
                }
            }
        }
    }

    private fun setToolBar() {
        setToolBarBack(true)
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun getToolbar(): Toolbar? {
         return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    private fun setReposRecyclerView() {
        rv_repo_list.layoutManager = LinearLayoutManager(this)
        repoListAdapter = RepoListAdapter(null)
        repoListAdapter.openLoadAnimation()
        repoListAdapter.setOnItemClickListener { adapter, view, position ->

            val data = adapter.data[position]
            if (data is Repo) {
                val intent = Intent(this, RepoDetailActivity::class.java)
                val mBundle = Bundle()
                mBundle.putParcelable(IntentConstant.REPO, data)
                intent.putExtras(mBundle)
                startActivity(intent)
            }else if(data is TrendingItem) {

            }
        }
        rv_repo_list.adapter = repoListAdapter
    }

    fun loadData(lists: List<Parcelable>) {
        if (lists.size == 0) {
            repoListAdapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null, false))
        } else {
            repoListAdapter.setNewData(lists)
        }
    }
}