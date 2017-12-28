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
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.edit
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.mvp.presenter.ProfilePresenter
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import com.gkzxhn.mygithub.ui.activity.UserActivity
import com.gkzxhn.mygithub.ui.adapter.IconListAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_profile.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/23.
 */
class ProfileFragment : BaseFragment(), BaseView {

    private lateinit var token: String
    private lateinit var iconAdapter: IconListAdapter
    private lateinit var loading: LVGhost

    @Inject
    lateinit var presenter: ProfilePresenter

    override fun launchActivity(intent: Intent) {
    }

    override fun showLoading() {
        /*loading = LVGhost(context)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.startAnim()
        fl_profile.addView(loading)*/
        progress.visibility = View.VISIBLE
        rv_organization.visibility = View.GONE
    }

    override fun hideLoading() {
        progress.visibility = View.GONE
        rv_organization.visibility = View.VISIBLE
        /*loading.stopAnim()
        fl_profile.removeView(loading)*/
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

        val url = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.AVATAR_URL, "")
        if (!TextUtils.isEmpty(url)) {
            iv_avatar_small.load(context, url, R.drawable.default_avatar)
        }
        token = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.ACCESS_TOKEN, "")
        if (TextUtils.isEmpty(token)) {
//            fl_to_login.visibility = View.VISIBLE
            rv_organization.visibility = View.GONE
            tv_username.text = "您还未登录,请登录..."

        } else {
//            fl_to_login.visibility = View.GONE
            rv_organization.visibility = View.VISIBLE
            getNewData()
        }

        setOrgRecyclerView()
        setClickListener()

    }

    private fun setClickListener() {
        account_view.setOnClickListener {
            if (TextUtils.isEmpty(token)) {
                startActivity(Intent(context, LoginActivity::class.java))
            } else {
                val intent = Intent(context, UserActivity::class.java)
                intent.action = IntentConstant.MINE_ACTION
                startActivity(intent)
            }
        }
        setting_layout.setOnClickListener {
            /*var intent = Intent(context,SettingActivity::class.java)
            startActivity(intent)*/
            SharedPreConstant.USER_SP.getSharedPreference()
                    .edit {
                        remove(SharedPreConstant.ACCESS_TOKEN)
                        putString(SharedPreConstant.USER_NAME, "")
                    }
            var intent = Intent(context, LoginActivity::class.java)
            startActivity(intent)
            //activity.finish()
        }
    }

    /**
     * 设置组织头像列表
     */
    private fun setOrgRecyclerView() {
        rv_organization.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        iconAdapter = IconListAdapter(null)
        iconAdapter.setOnItemClickListener { adapter, view, position ->

            val org = adapter.data[position] as Organization
            val intent = Intent(context, UserActivity::class.java)
            val data = Bundle()
            data.putParcelable(IntentConstant.USER, org)
            intent.putExtras(data)
            startActivity(intent)
        }
        rv_organization.adapter = iconAdapter
    }

    fun getNewData() {
        token = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.ACCESS_TOKEN, "")
//        fl_to_login.visibility = View.GONE
        rv_organization.visibility = View.VISIBLE
        user_avatar.load(context, SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.AVATAR_URL, ""), R.drawable.default_avatar)
        tv_username.text = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.USER_NAME, "")
        tv_bio.text = SharedPreConstant.USER_SP.getSharedPreference()
                .getString(SharedPreConstant.USER_BIO, "")
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

    fun loadData(orgs: List<Organization>) {
        iconAdapter.setNewData(orgs)
    }
}