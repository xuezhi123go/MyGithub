package com.gkzxhn.mygithub.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.ui.fragment.ContributorsFragment
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
            Log.i(javaClass.simpleName, "verticalOffset -- : $verticalOffset total -- ${appBarLayout.totalScrollRange}")
            if (verticalOffset == 0) {
                //完全展开状态
                toolbar.navigationIcon = null
                rl_repo_head.visibility = View.VISIBLE
                rl_repo_head.background.alpha = 255
            } else if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                //appBarLayout.getTotalScrollRange() == 100
                //完全折叠
                iv_avatar_small.visibility = View.VISIBLE
                toolbar_title.visibility = View.VISIBLE
                toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
                rl_repo_head.visibility = View.INVISIBLE
            }else {
                iv_avatar_small.visibility = View.INVISIBLE
                toolbar_title.visibility = View.INVISIBLE
                rl_repo_head.visibility = View.VISIBLE
                val alpha = (1 - Math.abs(verticalOffset.toFloat()) / appBarLayout.totalScrollRange) * 255f
                Log.i(javaClass.simpleName, "alpha : $alpha ~~")
                rl_repo_head.background.alpha = alpha.toInt()
            }
        }
        setToolBar()
        initFragments()
    }

    @SuppressLint("ResourceAsColor")
    private fun setToolBar() {
        setToolBarBack(true)
        toolbar.title = ""
        toolbar_title.text = repo.name
        tv_repo_name.text = repo.name
        tv_code.text = repo.language
        repo_desc.text = repo.description
        iv_avatar.load(this, repo.owner.avatar_url, R.drawable.default_avatar)
        iv_avatar_small.load(this, repo.owner.avatar_url, R.drawable.default_avatar)
        tv_username.text = repo.owner.login
        tv_create_time.text = repo.created_at.substring(0, repo.created_at.indexOf("T"))
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

    private fun initFragments(){
        mFragments = arrayListOf()
        val contributorsFragment = ContributorsFragment(repo, IntentConstant.CONTRIBUTORS)
        val forksFragment = ContributorsFragment(repo, IntentConstant.FORKS)
        mFragments.add(contributorsFragment)
        mFragments.add(forksFragment)
        mFragments.add(IssueFragment(repo))

        vp_repo.adapter = object : FragmentPagerAdapter(supportFragmentManager){
            override fun getItem(position: Int): Fragment = mFragments[position]

            override fun getCount(): Int = mTabs.size

            override fun getPageTitle(position: Int): CharSequence = mTabs[position]
        }
        vp_repo.offscreenPageLimit = 3
        tb_repo.setupWithViewPager(vp_repo)
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

   /* override fun getStatusBar(): View? {
        return status_view
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu)
        return true
    }
}