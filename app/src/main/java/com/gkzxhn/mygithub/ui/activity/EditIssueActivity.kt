package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.FrameLayout
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.EditIssuePresenter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.activity_edit_issue.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/27.
 */
class EditIssueActivity : BaseActivity(), BaseView {

    @Inject
    lateinit var presenter: EditIssuePresenter

    override fun launchActivity(intent: Intent) {

    }

    private lateinit var loading : LVGhost

    override fun showLoading() {
        loading = LVGhost(this)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.startAnim()
        fl_body.addView(loading)
    }

    override fun hideLoading() {
        loading.stopAnim()
        fl_body.removeView(loading)
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    private lateinit var repoName: String
    private lateinit var userName: String


    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_edit_issue)
        repoName = intent.getStringExtra(IntentConstant.REPO)
        userName = intent.getStringExtra(IntentConstant.NAME)

        setToolbar()
        bt_post_issue.setOnClickListener { v ->
            val title = edit_title.text.toString().trim()
            val body = edit_body.text.toString()
            if (TextUtils.isEmpty(title)) {
                toast("标题必须填写...")
                return@setOnClickListener
            }
            presenter.postIssue(userName, repoName, title, body)
        }
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    private fun setToolbar() {
        setToolBarBack(true)
        toolbar.title = repoName
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }
}