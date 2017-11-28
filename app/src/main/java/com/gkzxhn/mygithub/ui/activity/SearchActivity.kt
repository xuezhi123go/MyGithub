package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.KeyEvent
import android.view.View
import android.widget.FrameLayout
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.hideSoftInput
import com.gkzxhn.mygithub.extension.showSoftInputFromWindow
import com.gkzxhn.mygithub.mvp.presenter.SearchPresenter
import com.gkzxhn.mygithub.ui.fragment.ContributorsFragment
import com.gkzxhn.mygithub.ui.fragment.RepoListFragment
import com.ldoublem.loadingviewlib.view.LVNews
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/13.
 */

class SearchActivity : BaseView, BaseActivity() {

    @Inject lateinit var presenter: SearchPresenter

    private lateinit var q: String
    private lateinit var loading: LVNews
    private var isLoading: Boolean = false

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        isLoading = true
        loading = LVNews(this)
        val params = FrameLayout.LayoutParams(50f.dp2px().toInt(), 50f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.setViewColor(R.color.text_black)
        loading.startAnim()
        fl_search.addView(loading)
    }

    override fun hideLoading() {
        isLoading = false
        loading.stopAnim()
        fl_search.removeView(loading)
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_search)
        setClickListener()
        setEditText()
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    private fun setEditText() {
        et_search.showSoftInputFromWindow(this)
    }

    private fun setClickListener() {
        tv_search.setOnClickListener{
            search()
        }
        et_search.setOnKeyListener {
            v, keyCode, event ->
            //是否是回车键
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                //搜索
                search();
            }
            return@setOnKeyListener false
        }

        iv_back.setOnClickListener{
            finish()
        }
    }

    private fun search() {
        if (isLoading) {
            return
        }
        q = et_search.text.toString().trim()
        if (TextUtils.isEmpty(q)) {
            return
        }
        vp_search.visibility = View.GONE
        tb_search.visibility = View.GONE
        et_search.hideSoftInput(this)
        presenter.searchRepos(q)
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    private lateinit var mFragments: ArrayList<Fragment>
    val mTabs = listOf<String>("repositories", "users")

    fun initFragment(list: List<Repo>){
        vp_search.visibility = View.VISIBLE
        tb_search.visibility = View.VISIBLE
        mFragments = arrayListOf()
        mFragments.add(RepoListFragment(list))
        mFragments.add(ContributorsFragment(null, IntentConstant.USERS, q))
        vp_search.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int = 2

            override fun getPageTitle(position: Int): CharSequence {
                return mTabs[position]
            }
        }
        tb_search.setupWithViewPager(vp_search)
    }
}