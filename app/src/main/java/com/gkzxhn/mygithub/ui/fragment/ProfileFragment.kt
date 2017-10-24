package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.mvp.presenter.ProfilePresenter
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import com.gkzxhn.mygithub.ui.adapter.RepoListAdapter
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/23.
 */
class ProfileFragment : BaseFragment(),BaseView {

    private lateinit var repoListAdapter: RepoListAdapter

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun launchActivity(intent: Intent) {
    }

    override fun showLoading() {
    }

    override fun hideLoading() {
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    private val LOGIN_REQUEST = 1000
    private val RESULT_OK = 1

    override fun initContentView() {
        tv_to_login.setOnClickListener {
            val intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
        }

        rv_profile.layoutManager = LinearLayoutManager(context)
        repoListAdapter = RepoListAdapter(null)
        rv_profile.adapter = repoListAdapter
        val token = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.ACCESS_TOKEN, "")
        if (TextUtils.isEmpty(token)) {
            fl_to_login.visibility = View.VISIBLE
        }else {
            fl_to_login.visibility = View.GONE
            presenter.loadData()
        }
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

    fun loadData(lists : List<Repo>){
        repoListAdapter.setNewData(lists)
    }
}