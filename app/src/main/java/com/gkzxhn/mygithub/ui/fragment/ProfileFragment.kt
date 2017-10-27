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
import com.chad.library.adapter.base.BaseQuickAdapter
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.mvp.presenter.ProfilePresenter
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import com.gkzxhn.mygithub.ui.activity.RepoDetailActivity
import com.gkzxhn.mygithub.ui.adapter.RepoListAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/23.
 */
class ProfileFragment : BaseFragment(), BaseView {

    private lateinit var repoListAdapter: RepoListAdapter
    private lateinit var loading: LVGhost

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun launchActivity(intent: Intent) {
    }

    override fun showLoading() {
        loading = LVGhost(context)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.startAnim()
        fl_profile.addView(loading)

    }

    override fun hideLoading() {
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

        rv_profile.layoutManager = LinearLayoutManager(context)
        repoListAdapter = RepoListAdapter(null)
        repoListAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN)
        repoListAdapter.setOnItemClickListener { adapter, view, position ->

            val repo = adapter.data[position] as Repo
            val intent = Intent(context, RepoDetailActivity::class.java)
            val mBundle = Bundle()
            mBundle.putParcelable(IntentConstant.REPO, repo)
            intent.putExtras(mBundle)
            startActivity(intent)
        }
        rv_profile.adapter = repoListAdapter
        val token = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.ACCESS_TOKEN, "")
        if (TextUtils.isEmpty(token)) {
            fl_to_login.visibility = View.VISIBLE
        } else {
            getNewData()
        }
    }

    fun getNewData() {
        fl_to_login.visibility = View.GONE
        presenter.loadData()
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

    fun loadData(lists: List<Repo>) {
        repoListAdapter.setNewData(lists)
    }
}