package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import kotlinx.android.synthetic.main.activity_repo_detail.*

/**
 * Created by 方 on 2017/10/25.
 */
class RepoDetailActivity:BaseActivity(),BaseView {

    val mTabs = listOf<String>("contributors", "forks", "issues")
    private lateinit var mFragments: ArrayList<Fragment>
    private lateinit var repo : Repo

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

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_repo_detail)
        repo = intent.getParcelableExtra<Repo>(IntentConstant.REPO)

        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            if (verticalOffset == 0) {
                //完全展开状态
                iv_avatar_small.visibility = View.INVISIBLE
            } else if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                //appBarLayout.getTotalScrollRange() == 100
                //完全折叠
                iv_avatar_small.visibility = View.VISIBLE
            }
        }
        setToolBar()
        initFragments()
    }

    private fun setToolBar() {
        setToolBarBack(true)
        toolbar.title = repo.name
        iv_avatar_small.load(this, repo.owner.avatar_url, R.drawable.default_avatar)
        iv_avatar_big.load(this, repo.owner.avatar_url, R.drawable.default_avatar)
        setToolbarMenuClickListener(object : Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item!!.itemId) {
                    R.id.toolbar_add_issue -> {
                        val intent = Intent(this@RepoDetailActivity, EditIssueActivity::class.java)
                        intent.putExtra(IntentConstant.NAME, repo.owner.login)
                        intent.putExtra(IntentConstant.REPO, repo.name)
                        startActivity(intent)
                    }
                }
                return true
            }
        })
    }

    fun initFragments(){
        mFragments = arrayListOf()
        mFragments.add(IssueFragment(repo))
        mFragments.add(IssueFragment(repo))
        mFragments.add(IssueFragment(repo))

        vp_repo.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment = mFragments[position]

            override fun getCount(): Int = mTabs.size

            override fun getPageTitle(position: Int): CharSequence = mTabs[position]
        }
        tb_repo.setupWithViewPager(vp_repo)
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

   /* override fun getStatusBar(): View? {
        return status_view
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        return true
    }
}