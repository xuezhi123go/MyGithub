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
import com.gkzxhn.mygithub.constant.Constant
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.mvp.presenter.ActivityPresenter
import com.gkzxhn.mygithub.ui.activity.IssueDetailActivity
import com.gkzxhn.mygithub.ui.activity.RepoDetailActivity
import com.gkzxhn.mygithub.ui.adapter.ActivityAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_notifications.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/12/13.
 */
class ActivityFragment : BaseFragment(), BaseView {

    private lateinit var loading: LVGhost

    @Inject
    lateinit var presenter: ActivityPresenter

    private lateinit var adapter: ActivityAdapter

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        loading = LVGhost(context)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
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
            getNewData()
        }
        getNewData()
        adapter = ActivityAdapter(null)
        rv_notifications.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener { adapter, view, position ->
            var type = (adapter.data[position] as Event).type
            var fullName = (adapter.data[position] as Event).repo.name
            var s = fullName.split("/")
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
        Log.i(javaClass.simpleName, event[0].toString())
    }

    fun getNewData() {
        var string: String = SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.USER_NAME, "")
        Log.i(javaClass.simpleName, "USER_NAME = " + string)
        presenter.getEvents(SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.USER_NAME, ""))
    }


}