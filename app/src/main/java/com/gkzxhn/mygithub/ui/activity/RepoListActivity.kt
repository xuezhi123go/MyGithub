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
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.bean.info.ItemBean
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.SearchUserResult
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.mvp.presenter.RepoListPresenter
import com.gkzxhn.mygithub.ui.adapter.RepoListAdapter
import com.gkzxhn.mygithub.ui.adapter.UserListAdapter
import com.gkzxhn.mygithub.ui.wedgit.RecycleViewDivider
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.activity_repo_list.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/27.
 */
class RepoListActivity : BaseActivity(), BaseView {

    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var userListAdapter: UserListAdapter
    private lateinit var action: String
    private lateinit var loading: LVGhost

    @Inject lateinit var presenter: RepoListPresenter

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
        loading?.let { it.stopAnim() }
        ll_repo_list.removeView(loading)
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        isOn = true
        setContentView(R.layout.activity_repo_list)
        setToolBar()

        action = intent.action
        when (action) {
            IntentConstant.MY_REPOS -> {
                toolbar.title = SharedPreConstant.USER_SP.getSharedPreference()
                        .getString(SharedPreConstant.USER_NAME, "")
                setReposRecyclerView()
                presenter.loadRepos()
            }
            IntentConstant.ORG_REPOS -> {
                val org = intent.getStringExtra(IntentConstant.ORG_NAME)
                toolbar.title = org
                setReposRecyclerView()
                presenter.loadOrgRepos(org)
            }
            IntentConstant.TRENDING_REPO -> {
                val list = intent.getParcelableArrayListExtra<ItemBean>(IntentConstant.REPO_ENTITIES)
                toolbar.title = "仓库周榜"
                setReposRecyclerView()
                if (list.size > 0) {
                    repoListAdapter.setNewData(list as List<Parcelable>?)
                } else {
                    presenter.getTrendingRepo()
                }
            }
            IntentConstant.USERS -> {
                val list = intent.getParcelableArrayListExtra<Icon2Name>(IntentConstant.USERS)
                toolbar.title = "大牛榜"
                setUsersRecyclerView()
                if (list.size > 0) {
                    userListAdapter.setNewData(list as List<Parcelable>?)
                    presenter.getUserBio(list, this)
                } else {
                    presenter.getPopularUser()
                }
            }
        }
    }

    private fun setUsersRecyclerView() {
        rv_repo_list.layoutManager = LinearLayoutManager(this)
        userListAdapter = UserListAdapter(null)
        userListAdapter.openLoadAnimation()
        userListAdapter.setOnItemClickListener { adapter, view, position ->

            val data = adapter.data[position] as Parcelable
            val intent = Intent(this, UserActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(IntentConstant.User, data)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        rv_repo_list.adapter = userListAdapter
        rv_repo_list.addItemDecoration(RecycleViewDivider(this, LinearLayoutManager.HORIZONTAL, 2,
                App.getInstance().resources.getColor(R.color.gray_back)))
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
            } else if (data is ItemBean) {

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

    fun loadPopUsers(result: SearchUserResult) {
        val list = result.items
                .map { item ->
                    return@map Icon2Name(item.avatar_url, item.login, "user")
                }
        userListAdapter.setNewData(list)
        presenter.getUserBio(list, this)
    }

    fun updateList(position: Int, data: Any) {
        userListAdapter.data[position] = data
        userListAdapter.notifyItemChanged(position, data)
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

    var isOn = false
    override fun onDestroy() {
        isOn = false
        super.onDestroy()
    }
}