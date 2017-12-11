package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
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
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.EventPresenter
import com.gkzxhn.mygithub.ui.activity.RepoDetailActivity
import com.gkzxhn.mygithub.ui.adapter.EventAdapter
import com.gkzxhn.mygithub.utils.SPUtil
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

        adapter = EventAdapter(null)
        rv_notifications.layoutManager = LinearLayoutManager(context)
        adapter.setOnItemClickListener { adapter, view, position ->

            var name = (adapter.data[position] as Event).repo.name.split("/")
            var owenr = name[0]
            var repo = name[1]
            presenter.getRepoDetail(owenr, repo)
        }
        rv_notifications.adapter = adapter

    }

    override fun getStatusBar(): View? {
        return status_view_notifications
    }

    override fun getToolbar(): Toolbar? {
        return toolbar_notifications
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        //repo = intent.getParcelableExtra<Repo>(IntentConstant.REPO)

        return inflater!!.inflate(R.layout.fragment_notifications, container, false)
    }

    fun loadData(event: List<Event>) {
        adapter.setNewData(event)
        Log.i(javaClass.simpleName, event[0].toString())
    }

    fun getNewData() {
        var string:String = SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.USER_NAME, "")
        Log.i(javaClass.simpleName,"USER_NAME = "+string)
        presenter.getEvents(SharedPreConstant.USER_SP.getSharedPreference().getString(SharedPreConstant.USER_NAME, ""))
    }

    fun toRepoDetailActivity(repo: Repo) {
        val intent = Intent(context, RepoDetailActivity::class.java)
        val mBundle = Bundle()
        mBundle.putParcelable(IntentConstant.REPO, repo)
        intent.putExtras(mBundle)
        startActivity(intent)
    }

    fun test1() {
        var old = SPUtil.get(context, "event", "")
        if (!"a".equals(old)) {
            context.toast("空了")
        }
    }
}