package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.showSoftInputFromWindow
import com.gkzxhn.mygithub.mvp.presenter.SearchPresenter
import kotlinx.android.synthetic.main.activity_search.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/11/13.
 */

class SearchActivity : BaseView, BaseActivity() {

    @Inject lateinit var presenter: SearchPresenter

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
        ll_search.setOnClickListener{

        }
        iv_back.setOnClickListener{
            finish()
        }
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }
}