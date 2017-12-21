package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.util.Log
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
import com.gkzxhn.mygithub.mvp.presenter.FileDetailPresenter
import com.ldoublem.loadingviewlib.view.LVNews
import com.pddstudio.highlightjs.models.Language
import com.pddstudio.highlightjs.models.Theme
import kotlinx.android.synthetic.main.activity_file_detail.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/12/20.
 */
class FileDetailActivity : BaseActivity(), BaseView {

    @Inject
    lateinit var presenter: FileDetailPresenter

    override fun launchActivity(intent: Intent) {

    }

    private lateinit var loading: LVNews

    override fun showLoading() {
        loading = LVNews(this)
        val params = FrameLayout.LayoutParams(50f.dp2px().toInt(), 50f.dp2px().toInt(), Gravity.CENTER)
        loading.layoutParams = params
        loading.setViewColor(R.color.text_black)
        loading.startAnim()
        fl_content.addView(loading)
    }

    override fun hideLoading() {
        loading.stopAnim()
        fl_content.removeView(loading)
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

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_file_detail)
        getBaseData()
        setToolbar()
        getContent()
    }

    private lateinit var login: String
    private lateinit var repoName: String
    private lateinit var path: String

    private fun getBaseData() {
        login = intent.getStringExtra(IntentConstant.NAME)
        repoName = intent.getStringExtra(IntentConstant.REPO)
        path = intent.getStringExtra(IntentConstant.PATH)
    }

    private fun setToolbar() {
        toolbar.title = path.substringAfterLast("/")
        setToolBarBack(true)
    }

    private fun getContent() {
        //register theme change listener
//        highlight_js_view.setOnThemeChangedListener(this)
        //change theme and set language to auto detect
        highlight_js_view.setTheme(Theme.ARDUINO_LIGHT)
        highlight_js_view.setHighlightLanguage(Language.AUTO_DETECT)
        //load the source (can be loaded by String, File or URL)
        presenter.getCode(login, repoName, path)
    }

    fun loadData(base64Decode: String) {
        Log.i(javaClass.simpleName, "content : $base64Decode")
        highlight_js_view.setSource(base64Decode)
    }
}