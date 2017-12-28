package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Event
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.Constant
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.mvp.presenter.EventPresenter
import com.gkzxhn.mygithub.ui.activity.IssueDetailActivity
import com.gkzxhn.mygithub.ui.activity.IssuesActivity
import com.gkzxhn.mygithub.ui.activity.RepoDetailActivity
import com.gkzxhn.mygithub.ui.adapter.EventAdapter
import com.gkzxhn.mygithub.utils.SPUtil
import com.gkzxhn.mygithub.utils.Utils
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_notifications.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/11/19.
 */
class EventFragment : BaseFragment(), BaseView {

    private lateinit var loading: LVGhost
    private lateinit var event: Event

    @Inject
    lateinit var presenter: EventPresenter

    private lateinit var adapter: EventAdapter


    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        loading = LVGhost(context)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params as ViewGroup.LayoutParams?
        loading.startAnim()
        fl_notifications.addView(loading)
    }

    override fun hideLoading() {
        srl_notifications?.let {
            it.isRefreshing = false
        }
        loading.stopAnim()
        fl_notifications.removeView(loading)

    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initContentView() {

        presenter.subscribe()

        srl_notifications.setOnRefreshListener {
            //下拉刷新
            getNewData()
        }
        getNewData()

        adapter = EventAdapter(null, activity)
        rv_notifications.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener { adapter, view, position ->
            var type = (adapter.data[position] as Event).type
            var fullName = (adapter.data[position] as Event).repo.name
            var s = fullName.split("/")
            var time = Utils.parseDate((adapter.data[position] as Event).created_at, "yyyy-MM-dd'T'HH:mm:ss'Z'") + 8 * 60 * 60 * 1000
            SPUtil.put(context, SharedPreConstant.LAST_TIME, time)
            /*这里点击item之后刷新当前item，由于加载完数据之后已经记录了新的lasttime，所以刷新之后新消息标志会隐藏*/
            adapter.notifyItemChanged(position)

            when (type) {"IssuesEvent", "IssueCommentEvent" -> {
                val issue = (adapter.data[position] as Event).payload.issue
                val name = s[0]
                val repo = s[1]
                val number = issue.number
                val time = issue.created_at
                val avatar = issue.user.avatar_url
                val body = issue.body
                val title = issue.title
                val intent = Intent(context, IssueDetailActivity::class.java)
                intent.putExtra(IntentConstant.NAME, name)
                intent.putExtra(IntentConstant.REPO, repo)
                intent.putExtra(IntentConstant.ISSUE_NUM, number)
                intent.putExtra(IntentConstant.CREATE_TIME, time)
                intent.putExtra(IntentConstant.AVATAR, avatar)
                intent.putExtra(IntentConstant.BODY, body)
                intent.putExtra(IntentConstant.TITLE, title)
                startActivity(intent)
            }
                else -> {
                    val intent = Intent(context, RepoDetailActivity::class.java)
                    intent.putExtra(IntentConstant.FULL_NAME, fullName)
                    startActivity(intent)
                }
            }
        }
        rv_notifications.adapter = adapter
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        Constant.CURRENT_PAGE = javaClass.simpleName

        return inflater!!.inflate(R.layout.fragment_notifications, container, false)
    }

    fun loadData(event: List<Event>) {
        adapter.setNewData(event)
        adapter.loadMoreEnd()
        Log.i(javaClass.simpleName, event[0].toString())
    }

    fun getNewData() {
//        var time = SPUtil.get(context, SharedPreConstant.LAST_TIME, 1L) as Long
//        SPUtil.put(activity, SharedPreConstant.LAST_TIME, time)
        var string: String = SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.USER_NAME, "")
        Log.i(javaClass.simpleName, "USER_NAME = " + string)
        presenter.getEvents(SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.USER_NAME, ""))
    }

    fun toIssues(repo: Repo) {
        val intent = Intent(context, IssuesActivity::class.java)
        val mBundle = Bundle()
        mBundle.putParcelable(IntentConstant.REPO, repo)
        intent.putExtras(mBundle)
        startActivity(intent)
        hideLoading()
    }
}