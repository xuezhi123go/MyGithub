package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.RelativeLayout
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Comment
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.IssueDetailPresenter
import com.gkzxhn.mygithub.ui.adapter.IssueDetailAdapter
import com.gkzxhn.mygithub.utils.PopupWindowUtil
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.activity_issue_detail.*
import javax.inject.Inject


/**
 * Created by 方 on 2017/10/26.
 */
class IssueDetailActivity: BaseActivity(), BaseView {
    private lateinit var adapter : IssueDetailAdapter

    private lateinit var loading : LVGhost

    @Inject
    lateinit var presenter: IssueDetailPresenter

    private lateinit var repoName : String

    override fun launchActivity(intent: Intent) {

    }

    override fun showLoading() {
        loading = LVGhost(this)
        val params = RelativeLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt())
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            loading.elevation = 4f.dp2px()
        }
        loading.layoutParams = params
        loading.startAnim()
        ll_issue_detail.addView(loading, 2)
    }

    override fun hideLoading() {
        loading.stopAnim()
        ll_issue_detail.removeView(loading)
    }

    override fun showMessage() {
    }

    override fun killMyself() {
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_issue_detail)

        val name = intent.getStringExtra(IntentConstant.NAME)
        repoName = intent.getStringExtra(IntentConstant.REPO)
        val number = intent.getIntExtra(IntentConstant.ISSUE_NUM, 0)

        setToolBar()
        initRecyclerView()
        initReplyView()

        presenter.getComments(name, repoName, number)
    }

    private fun setToolBar() {
        toolbar.title = repoName
        setToolBarBack(true)
    }

    private fun initReplyView() {
        tv_reply.setOnClickListener {
            view ->
            PopupWindowUtil.liveCommentEdit(this, view, {
                confirmed, comment ->
                if (TextUtils.isEmpty(comment)) {
                    toast("您还未输入任何内容")
                    return@liveCommentEdit
                }else{
                    presenter.postComment(comment)
                }
            })
        }
    }

    private fun initRecyclerView() {
        adapter = IssueDetailAdapter(null)
        adapter.openLoadAnimation()
        rv_issue_detail.layoutManager = LinearLayoutManager(this)
        rv_issue_detail.adapter = adapter
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun getToolbar(): Toolbar? = toolbar

    override fun getStatusBar()=status_view

    fun loadData(comments: List<Comment>){
        if (comments.size == 0) {
            adapter.setEmptyView(LayoutInflater.from(this).inflate(R.layout.empty_view, null, false))
        }
        adapter.setNewData(comments)

    }

    fun addComment(comment: Comment) {
        adapter.addData(0, comment)
    }
}