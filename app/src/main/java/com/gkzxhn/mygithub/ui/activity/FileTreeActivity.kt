package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.ui.adapter.FileTreeTitleAdapter
import com.gkzxhn.mygithub.ui.fragment.FileListFragment
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.gkzxhn.mygithub.utils.rxbus.entity.NextPageEvent
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import kotlinx.android.synthetic.main.activity_file_tree.*

/**
 * Created by 方 on 2017/12/20.
 */
class FileTreeActivity : BaseActivity(), BaseView {
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

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_file_tree)
        subscribe()
        getBaseData()
        setToolbar()
        setFileTitle()
        initFragments()
    }

    private lateinit var login: String
    private lateinit var repoName: String

    private fun getBaseData() {
        login = intent.getStringExtra(IntentConstant.NAME)
        repoName = intent.getStringExtra(IntentConstant.REPO)
    }

    private fun setToolbar() {
        toolbar.title = repoName
        setToolBarBack(true)
    }

    private lateinit var titleList: MutableList<String>
    private lateinit var fileTreeTitleAdapter: FileTreeTitleAdapter

    private fun setFileTitle() {
        titleList = arrayListOf()
        titleList.add("/ >")
        rv_file_title.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        fileTreeTitleAdapter = FileTreeTitleAdapter(titleList as ArrayList<String>)
        rv_file_title.adapter = fileTreeTitleAdapter

        fileTreeTitleAdapter.setOnItemClickListener { adapter, view, position ->
            vp_content.currentItem = position
            titleList = (adapter.data as ArrayList<String>).take(position + 1).toMutableList()
            adapter.replaceData(titleList)
        }
    }

    private lateinit var mFragments: ArrayList<Fragment>
    private lateinit var vpAdapter: FragmentPagerAdapter

    private fun initFragments() {
        mFragments = arrayListOf()
        for (i in 0..1) {
            val fragment = FileListFragment(i)
            fragment.owner = login
            fragment.repoName = repoName
            mFragments.add(fragment)
        }
        vpAdapter= object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return mFragments[position]
            }

            override fun getCount(): Int {
                return mFragments.size
            }
        }
        vp_content.adapter = vpAdapter
        vp_content.offscreenPageLimit = 10
    }

    fun subscribe() {
        RxBus.instance.toFlowable(NextPageEvent::class.java)
                .bindToLifecycle(this)
                .subscribe(
                        {event ->
                            //保证一直都预加载一个fragment
                            if (vp_content.currentItem + 1 == mFragments.size - 1) {
                                val fragment = FileListFragment(mFragments.size)
                                fragment.owner = login
                                fragment.repoName = repoName
                                mFragments.add(fragment)
                            }
                            val fileListFragment = mFragments[vp_content.currentItem + 1] as FileListFragment
                            if (fileListFragment.path != event.path) {
                                fileListFragment.path = event.path
                                fileListFragment.getNewData()
                            }
                            vpAdapter.notifyDataSetChanged()
                            vp_content.currentItem = vp_content.currentItem + 1

                            updateFileTitle(event.name)
                        },
                        {
                            e: Throwable ->
                            Log.e(javaClass.simpleName, e.message)
                        }
                )
    }

    private fun updateFileTitle(name: String) {
        fileTreeTitleAdapter.addData("$name >")
        fileTreeTitleAdapter.notifyDataSetChanged()
        rv_file_title.smoothScrollToPosition(fileTreeTitleAdapter.datas!!.size - 1)
    }
}
