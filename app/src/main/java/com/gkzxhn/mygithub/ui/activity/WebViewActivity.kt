package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import android.webkit.WebChromeClient
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.constant.IntentConstant
import kotlinx.android.synthetic.main.activity_webview.*
import java.net.URLDecoder

/**
 * Created by 方 on 2017/12/28.
 */
class WebViewActivity: BaseActivity(), BaseView {
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

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_webview)
        setToolbar()
        initWebView()
    }

    private fun setToolbar() {
        setToolBarBack(true)
        toolbar.title = intent.getStringExtra(IntentConstant.TITLE)
    }

    private lateinit var webSettings : WebSettings

    private fun initWebView() {
        val link = intent.getStringExtra(IntentConstant.LINK)
        val url = URLDecoder.decode(link)
        webSettings = web_view.settings
        webSettings.javaScriptEnabled = true

        web_view.setWebChromeClient(WebChromeClient())
        webSettings.pluginState = (WebSettings.PluginState.ON);
        webSettings.useWideViewPort = true
        webSettings.setSupportZoom(true)
        //不显示webview缩放按钮
        webSettings.displayZoomControls = false
        web_view.loadUrl(url)
        web_view.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String?): Boolean {
                view?.loadUrl(url)
                return true
            }
        }
    }

    override fun onBackPressed() {
        if (web_view.canGoBack()) {
            web_view.goBack();//返回上一页面
        }else {
            super.onBackPressed()
        }
    }

}