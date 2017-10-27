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
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.Starred
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.mvp.presenter.StarsPresenter
import com.gkzxhn.mygithub.ui.adapter.StarsAdapter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.fragment_profile.*
import kotlinx.android.synthetic.main.fragment_stars.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/10/25.
 */
class StarsFragment : BaseFragment(), BaseView {

    private lateinit var stars: Starred
    private lateinit var loading: LVGhost

    private lateinit var repo: Repo

    @Inject
    lateinit var presenter: StarsPresenter

    private lateinit var adapter: StarsAdapter

    override fun launchActivity(intent: Intent) {
    }

    override fun showLoading() {
        loading = LVGhost(context)
        val params = FrameLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.startAnim()
        fl_profile.addView(loading)
    }

    override fun hideLoading() {
        loading.stopAnim()
        fl_profile.removeView(loading)
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initContentView() {

        adapter = StarsAdapter(null)
        rv_stars.layoutManager = LinearLayoutManager(context)
        rv_stars.adapter = adapter

//        val full_name = stars.full_name
//        val description = stars.description
        var intent: Intent
        intent = Intent()
        //repo = intent.getParcelableExtra<Repo>(IntentConstant.REPO)
       // val username = repo.owner.login
        presenter.getStarrde()
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_stars, container, false)
    }

    override fun getStatusBar(): View? {
        return status_view_stars
    }

    override fun getToolbar(): Toolbar? {
        return toolbar_stars
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)

    }

    fun loadData(stars: List<Starred>) {
        Log.i(javaClass.simpleName, stars[0].toString())
        adapter.setNewData(stars)
    }

    fun getNewData() {
        presenter.getStarrde()
    }
}