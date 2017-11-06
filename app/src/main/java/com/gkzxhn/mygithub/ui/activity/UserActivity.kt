package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.design.widget.CollapsingToolbarLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Owner
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.mvp.presenter.ProfilePresenter
import com.gkzxhn.mygithub.ui.adapter.RepoListAdapter
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/3.
 */
class UserActivity : BaseActivity(), BaseView {

    private lateinit var data: Parcelable

    private lateinit var adapter : RepoListAdapter

    @Inject lateinit var presenter: ProfilePresenter

    private lateinit var username : String
    private lateinit var login : String

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        srl_repos?.let { it.isRefreshing = true }
    }

    override fun hideLoading() {
        srl_repos?.let { it.isRefreshing = false }
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_user)
        data = intent.getParcelableExtra<Parcelable>(IntentConstant.User)
        setToolBar()
        initAppBar()
        initRecyclerView()
    }

    private fun initAppBar() {
        if (data is Owner) {
            login = (data as Owner).login
            iv_avatar_big.load(this, (data as Owner).avatar_url, R.drawable.default_avatar)
            presenter.getUser(username)
        }else if (data is User) {
            login = (data as User).login
            username = (data as User).name
            iv_avatar_big.load(this, (data as User).avatar_url, R.drawable.default_avatar)
            tv_desc.text = (data as User).bio.let {
                if (!TextUtils.isEmpty(it)) {
                    return@let it
                }else {
                    return@let "nothing to say"
                }
            }
            tv_business.text = (data as User).company.let {
                if (!TextUtils.isEmpty(it)) {
                    return@let it
                }else {
                    return@let "unknow"
                }
            }
            tv_email.text = (data as User).email.let {
                if (!TextUtils.isEmpty(it)) {
                    return@let it
                }else {
                    return@let "unknow"
                }
            }
            tv_place.text = (data as User).location.let {
                if (!TextUtils.isEmpty(it)) {
                    return@let it
                }else {
                    return@let "unknow"
                }
            }
            tv_rss_feed.text = (data as User).blog.let {
                if (!TextUtils.isEmpty(it)) {
                    return@let it
                }else {
                    return@let "unknow"
                }
            }
        }
        tv_username.text = if (TextUtils.isEmpty(username)) login else username
        toolbar.title = ""
        toolbar_title.text = if (TextUtils.isEmpty(username)) login else username
        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                //完全展开状态
            } else if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                //appBarLayout.getTotalScrollRange() == 100
                //完全折叠
                toolbar_title.visibility = View.VISIBLE
            }else {
                toolbar_title.visibility = View.GONE
            }
        }
    }

    private fun initRecyclerView() {
        srl_repos.setOnRefreshListener {
            login?.let {
                presenter.loadUserRepos(it)
            }
        }
        adapter = RepoListAdapter(null)
        adapter.setOnItemClickListener { adapter, view, position ->

                    val repo = adapter.data[position] as Repo
                    val intent = Intent(this, RepoDetailActivity::class.java)
                    val mBundle = Bundle()
                    mBundle.putParcelable(IntentConstant.REPO, repo)
                    intent.putExtras(mBundle)
                    startActivity(intent)
                }
        adapter.openLoadAnimation()
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = adapter
        login?.let {
            presenter.loadUserRepos(it)
        }
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    private fun setToolBar() {
        val params = toolbar.layoutParams as CollapsingToolbarLayout.LayoutParams
        val statusHeight = getStatusHeight(this)
        params.topMargin = statusHeight
        setToolBarBack(true)
    }

    fun loadData(user : User){
        data = user
        initAppBar()
    }

    fun loadRepos(repos : List<Repo>){
        if (repos.size == 0) {
            adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null, false))
        }else {
            adapter.setNewData(repos)
        }
    }
}