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
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.bean.entity.IvTvItemBean
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.bean.info.Owner
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.extension.loadBlur
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.ProfilePresenter
import com.gkzxhn.mygithub.ui.adapter.IvTvAdapter
import kotlinx.android.synthetic.main.activity_user.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/3.
 */
class UserActivity : BaseActivity(), BaseView {

    private var data: Parcelable? = null

    private lateinit var adapter: IvTvAdapter

    @Inject lateinit var presenter: ProfilePresenter

    private lateinit var username: String
    private lateinit var login: String

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
        val action = intent.action
        if (action == IntentConstant.MINE_ACTION) {
            val localUser = presenter.getLocalUser()
            localUser?.let { data = it }
            pb_follow.visibility = View.GONE
        } else {
            data = intent.getParcelableExtra<Parcelable>(IntentConstant.USER)
        }
        setToolBar()
        initRecyclerView()
        if (null == data) {
            toast("请重新登录...")
            return
        } else {
            initBaseData()
            initFollowTopic()
            setOnclick()
            initAppBar()
        }
    }

    private fun initFollowTopic() {
        if (IntentConstant.MINE_ACTION != intent.action && adapter.type == "USER") {
            presenter.checkIfFollowIng(login)
        } else {
            pb_follow.visibility = View.GONE
        }
    }

    //    0表示已关注,1表示未关注,-1表示正在查询
    private var isFollowing = -1

    /**
     * 更新follow标签状态
     * @param isFollowing 0表示已关注,1表示未关注,-1表示正在查询
     */
    fun updateListFollowStatus(isFollowing: Int) {
        this.isFollowing = isFollowing
        when (isFollowing) {
            0 -> {
                pb_follow.visibility = View.GONE
                tv_follow.visibility = View.VISIBLE
                tv_follow.text = "Unfollow"
                tv_follow.setBackgroundResource(R.drawable.gray_btn_back)
                tv_follow.setTextColor(resources.getColor(R.color.text_black))
                val leftDrawable = resources.getDrawable(R.drawable.follow)
                leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                tv_follow.setCompoundDrawablePadding(3f.dp2px().toInt())//设置图片和text之间的间距
                tv_follow.setCompoundDrawables(leftDrawable, null, null, null)
            }
            1 -> {
                pb_follow.visibility = View.GONE
                tv_follow.visibility = View.VISIBLE
                tv_follow.text = "Follow"
                tv_follow.setTextColor(resources.getColor(R.color.white))
                tv_follow.setBackgroundResource(R.drawable.green_bg)
                val leftDrawable = resources.getDrawable(R.drawable.unfollow)
                leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                tv_follow.setCompoundDrawablePadding(3f.dp2px().toInt())//设置图片和text之间的间距
                tv_follow.setCompoundDrawables(leftDrawable, null, null, null)
            }
            else -> {
                pb_follow.visibility = View.VISIBLE
                tv_follow.visibility = View.GONE
            }
        }
    }

    private fun setOnclick() {
        ll_followers.setOnClickListener {
            val intent = Intent(this, RepoListActivity::class.java)
            intent.putExtra(IntentConstant.TOOLBAR_TITLE, "Followers")
            intent.putExtra(IntentConstant.NAME, login)
            intent.action = IntentConstant.USERS
            startActivity(intent)
        }

        ll_following.setOnClickListener {
            val intent = Intent(this, RepoListActivity::class.java)
            intent.putExtra(IntentConstant.TOOLBAR_TITLE, "Following")
            intent.putExtra(IntentConstant.NAME, login)
            intent.action = IntentConstant.USERS
            startActivity(intent)
        }

        ll_repositories.setOnClickListener {
            val intent = Intent(this, RepoListActivity::class.java)
            intent.putExtra(IntentConstant.TOOLBAR_TITLE, "Repositories")
            intent.putExtra(IntentConstant.NAME, login)
            intent.action = IntentConstant.REPO
            startActivity(intent)
        }

        tv_follow.setOnClickListener {
            when (isFollowing) {
                0 -> {
                    presenter.unFollowUser(login)
                }
                1 -> {
                    presenter.followUser(login)
                }
                else -> {
                }
            }
        }
    }

    var avatar_url = ""

    private fun initAppBar() {
        iv_avatar_big.load(this, avatar_url, R.drawable.default_avatar)
        iv_user_header.loadBlur(this, avatar_url)
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
            } else {
                toolbar_title.visibility = View.GONE
            }
        }
    }

    private fun initBaseData() {
        if (data is Owner) {
            login = (data as Owner).login
            username = login
            avatar_url = (data as Owner).avatar_url
            when ((data as Owner).type) {
                "User" -> {
                    adapter.type = "USER"
                    presenter.getUser(login)
                }
                "Organization" -> {
                    adapter.type = "Organization"
                    presenter.getOrg(login)
                }
                else -> {
                }
            }
        } else if (data is User) {
            login = (data as User).login
            avatar_url = (data as User).avatar_url
            username = if (TextUtils.isEmpty((data as User).name)) login else (data as User).name
            updateUserData()
        } else if (data is Icon2Name) {
            login = (data as Icon2Name).name
            username = login
            avatar_url = (data as Icon2Name).avatarUrl!!
            presenter.getUser(login)
        } else if (data is Organization) {
            login = (data as Organization).login
            username = login
            avatar_url = (data as Organization).avatar_url
            adapter.type = "Organization"
            presenter.getOrg(login)
        }
        srl_repos.setOnRefreshListener {
            when (adapter.type) {
                "USER" -> {
                    presenter.getUser(login)
                }
                "Organization" -> {
                    presenter.getOrg(login)
                }
                else -> {
                }
            }
        }
    }

    /**
     * 更新组织信息
     */
    private fun updateOrgData() {

        ll_followers.visibility = View.GONE
        ll_following.visibility = View.GONE
        tv_repositories.text = (data as Organization).public_repos.toString().let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "0"
            }
        }

        for (i in 0..2) {
            ivLeftResources.removeAt(1)
            tvTitleList.removeAt(1)
            rightTv.removeAt(1)
        }

        tvTitleList[0] = "Organization Name"
        tvTitleList[1] = (data as Organization).company.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }

        tvTitleList[2] = (data as Organization).email.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }
        tvTitleList[3] = (data as Organization).location.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }
        tvTitleList[4] = (data as Organization).blog.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }

        rightTv[0] = username
        list = ivLeftResources.mapIndexed { index, ivResource ->
            if (index == 0) {
                IvTvItemBean(ivResource, tvTitleList[index], rightTv[index], true)
            } else
                IvTvItemBean(ivResource, tvTitleList[index], rightTv[index], false)
        } as ArrayList<IvTvItemBean>

        adapter.setNewData(list)
    }

    /**
     * 更新用户信息
     */
    private fun updateUserData() {

        tv_followers.text = (data as User).followers.toString().let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "0"
            }
        }

        tv_repositories.text = (data as User).public_repos.toString().let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "0"
            }
        }
        tv_following.text = (data as User).following.toString().let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "0"
            }
        }

        tvTitleList[4] = (data as User).company.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }

        tvTitleList[6] = (data as User).email.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }
        tvTitleList[5] = (data as User).location.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }
        tvTitleList[7] = (data as User).blog.let {
            if (!TextUtils.isEmpty(it)) {
                return@let it
            } else {
                return@let "Not Set"
            }
        }

        rightTv[0] = username
        list = ivLeftResources.mapIndexed { index, ivResource ->
            if (index in 1..3)
                IvTvItemBean(ivResource, tvTitleList[index], rightTv[index], true)
            else if (index == 0) {
                IvTvItemBean(ivResource, tvTitleList[index], rightTv[index], true)
            } else
                IvTvItemBean(ivResource, tvTitleList[index], rightTv[index], false)
        } as ArrayList<IvTvItemBean>

        adapter.setNewData(list)
    }

    private var ivLeftResources =
            arrayListOf(R.drawable.user, R.drawable.star_repos, R.drawable.organization, R.drawable.public_activity,
                    R.drawable.company, R.drawable.location, R.drawable.email, R.drawable.link)
    private var tvTitleList = arrayListOf<String>("Name", "Starred Repos", "Organization", "Public Activity",
            "Company", "Location", "Email", "Link")
    private var rightTv = arrayListOf<Any>("name", R.drawable.right_arrow, R.drawable.right_arrow, R.drawable.right_arrow,
            "", "", "", "")
    private lateinit var list: ArrayList<IvTvItemBean>

    private fun initRecyclerView() {
        list = ivLeftResources.mapIndexed { index, ivResource ->
            if (index in 1..3)
                IvTvItemBean(ivResource, tvTitleList[index], rightTv[index], true)
            else
                IvTvItemBean(ivResource, tvTitleList[index], "", false)
        } as ArrayList<IvTvItemBean>

        adapter = IvTvAdapter(null)
        adapter.setOnItemClickListener { adapter, view, position ->
            /*val repo = adapter.data[position] as Repo
            val intent = Intent(this, RepoDetailActivity::class.java)
            val mBundle = Bundle()
            mBundle.putParcelable(IntentConstant.REPO, repo)
            intent.putExtras(mBundle)
            startActivity(intent)*/
            if ((adapter as IvTvAdapter).type == "USER") {

                when (position) {
                    1 -> {
                        val intent = Intent(this, RepoListActivity::class.java)
                        intent.putExtra(IntentConstant.TOOLBAR_TITLE, "Starred Repos")
                        intent.putExtra(IntentConstant.NAME, login)
                        intent.action = IntentConstant.REPO
                        startActivity(intent)
                    }
                    2 -> {
                        val intent = Intent(this, RepoListActivity::class.java)
                        intent.putExtra(IntentConstant.TOOLBAR_TITLE, "Organization")
                        intent.putExtra(IntentConstant.NAME, login)
                        intent.action = IntentConstant.USERS
                        startActivity(intent)
                    }
                    3 -> {
                        val intent = Intent(this, RepoListActivity::class.java)
                        intent.putExtra(IntentConstant.TOOLBAR_TITLE, "Activity")
                        intent.putExtra(IntentConstant.NAME, login)
                        intent.action = IntentConstant.ACTIVITY
                        startActivity(intent)
                    }
                    else -> {
                    }
                }
            }
        }
        adapter.openLoadAnimation()
        rv_user.layoutManager = LinearLayoutManager(this)
        rv_user.adapter = adapter
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

    fun loadData(user: Parcelable) {
        data = user
        if (data is User) {
            updateUserData()
        } else if (data is Organization) {
            updateOrgData()
        }
    }

    fun loadRepos(repos: List<Repo>) {
        if (repos.size == 0) {
            adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null, false))
        } else {
//            adapter.setNewData(repos)
        }
    }
}