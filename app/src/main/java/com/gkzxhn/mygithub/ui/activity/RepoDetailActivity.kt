package com.gkzxhn.mygithub.ui.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.LinearLayout
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Content
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.base64Decode
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.RepoDetailPresenter
import com.ldoublem.loadingviewlib.view.LVNews
import kotlinx.android.synthetic.main.activity_repo_detail.*
import javax.inject.Inject



/**
 * Created by 方 on 2017/10/25.
 */
class RepoDetailActivity:BaseActivity(),BaseView {

    val mTabs = listOf<String>("contributors", "forks", "issues")
    private lateinit var mFragments: ArrayList<Fragment>
    private var repo : Repo? = null
    private var isStarred = false
    private var isWatched = false

    private lateinit var loading: LVNews

    @Inject lateinit var presenter : RepoDetailPresenter

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

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initView(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_repo_detail)

        repo = intent.getParcelableExtra<Repo>(IntentConstant.REPO)
        setToolBarBack(true)
        toolbar.title = ""
        if (repo == null) {
            val fullName = intent.getStringExtra(IntentConstant.FULL_NAME)
            val list = fullName.split("/")
            if(list.size > 1)
                presenter.getRepoDetail(list[0], list[1])
            else
                toast("请求错误,请重试")
        }else {
            initViewWithData()
        }
    }

    private fun initViewWithData() {
        stars_count = repo!!.stargazers_count
        watchers_count = repo!!.watchers_count

        appbar.addOnOffsetChangedListener { appBarLayout, verticalOffset ->
            Log.i(javaClass.simpleName, "verticalOffset -- : $verticalOffset total -- ${appBarLayout.totalScrollRange}")
            if (verticalOffset == 0) {
                //完全展开状态
                rl_repo_head.visibility = View.VISIBLE
                iv_avatar_small.visibility = View.INVISIBLE
                toolbar_title.visibility = View.INVISIBLE
            } else if (Math.abs(verticalOffset) == appBarLayout.totalScrollRange) {
                //appBarLayout.getTotalScrollRange() == 100
                //完全折叠
                iv_avatar_small.visibility = View.VISIBLE
                toolbar_title.visibility = View.VISIBLE
                rl_repo_head.visibility = View.INVISIBLE
            } else {
                iv_avatar_small.visibility = View.INVISIBLE
                toolbar_title.visibility = View.INVISIBLE
                rl_repo_head.visibility = View.VISIBLE
    //                val alpha = (1 - Math.abs(verticalOffset.toFloat()) / appBarLayout.totalScrollRange) * 255f
    //                Log.i(javaClass.simpleName, "alpha : $alpha ~~")
    //                rl_repo_head.background.alpha = alpha.toInt()
            }
        }
        setToolBar()
        setBaseData()
        presenter.getReadme(repo!!.owner.login, repo!!.name)
        presenter.checkIfStarred(repo!!.owner.login, repo!!.name)
        presenter.checkIfWatched(repo!!.owner.login, repo!!.name)
        setOnclick()
    }

    private var stars_count = 0
    private var watchers_count = 0

    private fun setBaseData() {
        tv_watch.setText(watchers_count.toString())
        tv_stars.setText(stars_count.toString())
        tv_forks.setText(repo!!.forks_count.toString())
        repo_desc.text = repo!!.description
        tv_update_time.text = repo!!.updated_at.substring(0, repo!!.created_at.indexOf("T"))

        if (repo!!.fork) {
            iv_fork.setImageResource(R.drawable.fork_selected)
        }else {
            iv_fork.setImageResource(R.drawable.fork_normal)
        }
    }

    private fun setOnclick(){
        ll_watch.setOnClickListener{
            if (isWatched) {
                presenter.unwatchRepo(repo!!.owner.login, repo!!.name)
            }else {
                presenter.watchRepo(repo!!.owner.login, repo!!.name)
            }
        }

        ll_stars.setOnClickListener {
            if (isStarred) {
                presenter.unStarred(repo!!.owner.login, repo!!.name)
            }else {
                presenter.starRepo(repo!!.owner.login, repo!!.name)
            }
        }

        tv_reame_all.setOnClickListener {
            val layoutParams = md_view.layoutParams as LinearLayout.LayoutParams
            layoutParams.height = LinearLayout.LayoutParams.WRAP_CONTENT
            md_view.layoutParams = layoutParams

            tv_reame_all.visibility = View.GONE
        }
    }

    @SuppressLint("ResourceAsColor")
    private fun setToolBar() {
        toolbar_title.text = repo!!.name
        tv_repo_name.text = repo!!.name
        tv_code.text = repo!!.language
        iv_avatar.load(this, repo!!.owner.avatar_url, R.drawable.default_avatar)
        iv_avatar_small.load(this, repo!!.owner.avatar_url, R.drawable.default_avatar)
        tv_username.text = repo!!.owner.login

        setToolbarMenuClickListener(object : Toolbar.OnMenuItemClickListener{
            override fun onMenuItemClick(item: MenuItem?): Boolean {
                when(item!!.itemId) {
                    R.id.toolbar_add_issue -> {
                        val intent = Intent(this@RepoDetailActivity, EditIssueActivity::class.java)
                        intent.putExtra(IntentConstant.NAME, repo!!.owner.login)
                        intent.putExtra(IntentConstant.REPO, repo!!.name)
                        startActivity(intent)
                    }
                }
                return true
            }
        })
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

   /* override fun getStatusBar(): View? {
        return status_view
    }*/

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu)
        menu!!.getItem(0).setIcon(android.R.drawable.ic_menu_add)
        return true
    }

    fun starred(){
        isStarred = true
        iv_stars.setImageResource(R.drawable.star_selected)
    }

    fun updateStars(add : Boolean) {
        tv_stars.text = (if (add) ++stars_count else --stars_count).toString()
    }

    fun unStarred(){
        isStarred = false
        iv_stars.setImageResource(R.drawable.star_normal)
    }

    fun loadReadme(content: Content){
        val layoutParams = md_view.layoutParams as LinearLayout.LayoutParams
        layoutParams.height = 180f.dp2px().toInt()
        md_view.layoutParams = layoutParams

        tv_reame_all.visibility = View.VISIBLE
        md_view.setMDText(content.content.base64Decode())
    }

    fun showReadmeLoading() {
        pb_readme.visibility = View.VISIBLE
    }

    fun hideReadmeLoading() {
        pb_readme.visibility = View.GONE
    }

    fun showStarLoading() {
        pb_star.visibility = View.VISIBLE
        iv_stars.visibility = View.GONE
    }

    fun hideStarLoading() {
        pb_star.visibility = View.GONE
        iv_stars.visibility = View.VISIBLE
    }

    fun showWatchLoading() {
        pb_watch.visibility = View.VISIBLE
        iv_watch.visibility = View.GONE
    }

    fun hideWatchLoading() {
        pb_watch.visibility = View.GONE
        iv_watch.visibility = View.VISIBLE
    }

    fun watched() {
        isWatched = true
        iv_watch.setImageResource(R.drawable.watch_selected)
    }

    fun unwatched() {
        isWatched = false
        iv_watch.setImageResource(R.drawable.watch_normal)
    }

    fun updateWatch(add : Boolean) {
        tv_watch.text = (if (add) ++watchers_count else --watchers_count).toString()
    }

    fun initViewByData(repo: Repo) {
        this.repo = repo
        initViewWithData()
    }
}