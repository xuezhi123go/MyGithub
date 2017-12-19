package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.ui.fragment.IssueFragment
import kotlinx.android.synthetic.main.activity_issues.*

/**
 * Created by 方 on 2017/12/18.
 */
class IssuesActivity : BaseActivity(), BaseView {
    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_issues)
        repo = intent.getParcelableExtra<Repo>(IntentConstant.REPO)
        setToolBar()
        initFragment()
        setOnclick()
    }

    private fun setOnclick() {
        fab_add.setOnClickListener {
            //跳转编辑新Issue界面
            val intent = Intent(this, EditIssueActivity::class.java)
            intent.putExtra(IntentConstant.NAME, repo!!.owner.login)
            intent.putExtra(IntentConstant.REPO, repo!!.name)
            startActivity(intent)
        }
    }

    private  val mTabs = listOf("Open", "Closed")
    private lateinit var mFragments : ArrayList<Fragment>
    private lateinit var repo : Repo

    private fun initFragment() {
        mFragments = arrayListOf()
        mFragments.add(IssueFragment(repo , IntentConstant.OPEN))
        mFragments.add(IssueFragment(repo, IntentConstant.CLOSED))

        vp_issues.adapter = object : FragmentPagerAdapter(supportFragmentManager){

            override fun getPageTitle(position: Int): CharSequence {
                return mTabs[position]
            }

            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int = mTabs.size
        }

        tab_issues.setupWithViewPager(vp_issues)

        tab_issues.addOnTabSelectedListener(object : TabLayout.OnTabSelectedListener{
            override fun onTabReselected(tab: TabLayout.Tab?) {
            }

            override fun onTabUnselected(tab: TabLayout.Tab?) {
            }

            override fun onTabSelected(tab: TabLayout.Tab?) {
                Log.i(javaClass.simpleName, tab!!.text.toString())
                when (tab!!.text.toString()) {
                    "Open" -> {
                        appbar.setBackgroundResource(R.color.green)
                        fab_add.visibility = View.VISIBLE
                    }
                    "Closed" -> {
                        appbar.setBackgroundResource(R.color.red)
                        fab_add.visibility = View.GONE
                    }
                    else -> {
                    }
                }
            }
        })
    }

    override fun getToolbar(): Toolbar? = toolbar

    override fun getStatusBar(): View? = status_view

    private fun setToolBar() {
        toolbar.title = "Issue"
        setToolBarBack(true)
    }
}