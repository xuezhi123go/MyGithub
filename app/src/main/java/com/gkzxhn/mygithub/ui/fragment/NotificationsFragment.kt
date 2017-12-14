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
import com.gkzxhn.mygithub.bean.info.Notifications
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.mvp.presenter.NotificationsPresenter
import com.gkzxhn.mygithub.ui.adapter.NotificationsAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_notifications.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/11/15.
 */
class NotificationsFragment : BaseFragment(), BaseView {

    private lateinit var notifications: Notifications
    private lateinit var loading: LVGhost

    //private lateinit var repo:Repo

    //private lateinit var repo: Repo

    @Inject
    lateinit var presenter: NotificationsPresenter

    private lateinit var adapter: NotificationsAdapter


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
        srl_notifications.setOnRefreshListener {
            getNewData()
        }
        adapter = NotificationsAdapter(null)
        rv_notifications.layoutManager = LinearLayoutManager(context)
        rv_notifications.adapter = adapter
        presenter.subscribe()
        getNewData()
    }

   /* override fun getStatusBar(): View? {
        return status_view_notifications
    }

    override fun getToolbar(): Toolbar? {
        return toolbar_notifications
    }*/

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

    fun loadData(notifications: List<Notifications>) {
        Log.i(javaClass.simpleName, notifications[0].toString())
        adapter.setNewData(notifications)
    }

    fun getNewData() {
        presenter.getNotifications(
                //notifications.repository.owner.login, notifications.repository.name
        )
    }
}