package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.mvp.presenter.ProfilePresenter
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import com.gkzxhn.mygithub.ui.activity.RepoListActivity
import com.gkzxhn.mygithub.ui.adapter.IconListAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/23.
 */
class ProfileFragment : BaseFragment(), BaseView {

    private lateinit var iconAdapter: IconListAdapter
    private lateinit var loading: LVGhost

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun launchActivity(intent: Intent) {
    }

    override fun showLoading() {
        organization_layout.visibility = View.GONE
        repos_layout.visibility = View.GONE
        loading = LVGhost(context)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.startAnim()
        fl_profile.addView(loading)

    }

    override fun hideLoading() {
        organization_layout.visibility = View.VISIBLE
        repos_layout.visibility = View.VISIBLE
        loading.stopAnim()
        fl_profile.removeView(loading)
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    override fun initContentView() {
        presenter.subscribe()
        tv_to_login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        iv_avatar_small.load(context, SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.AVATAR_URL, ""), R.drawable.default_avatar)

        setOrgRecyclerView()
        setClickListener()

        val token = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.ACCESS_TOKEN, "")
        if (TextUtils.isEmpty(token)) {
            fl_to_login.visibility = View.VISIBLE
            organization_layout.visibility = View.GONE
            repos_layout.visibility = View.GONE
        } else {
            fl_to_login.visibility = View.GONE
            organization_layout.visibility = View.VISIBLE
            repos_layout.visibility = View.VISIBLE
            getNewData()
        }
    }

    private fun setClickListener() {
        repos_layout.setOnClickListener {
            val intent = Intent(context, RepoListActivity::class.java)
            intent.action = IntentConstant.MY_REPOS
            startActivity(intent)
        }
    }

    /**
     * 设置组织头像列表
     */
    private fun setOrgRecyclerView() {
        rv_organization.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        iconAdapter = IconListAdapter(null)
        iconAdapter.setOnItemClickListener { adapter, view, position ->
            val org = (adapter.data[position] as Organization).login
            val intent = Intent(context, RepoListActivity::class.java)
            intent.action = IntentConstant.ORG_REPOS
            intent.putExtra(IntentConstant.ORG_NAME, org)
            startActivity(intent)
        }
        rv_organization.adapter = iconAdapter
    }

    fun getNewData() {
        fl_to_login.visibility = View.GONE
        iv_avatar_small.load(context, SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.AVATAR_URL, ""), R.drawable.default_avatar)
        presenter.loadUserData(SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.USER_NAME, ""))
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    fun loadData(orgs: List<Organization>){
        iconAdapter.setNewData(orgs)
    }
}