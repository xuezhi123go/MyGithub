package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.view.KeyEvent
import android.widget.RelativeLayout
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.di.module.AuthModule
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.LoginPresenter
import com.ldoublem.loadingviewlib.view.LVGhost
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject


/**
 * Created by 方 on 2017/10/19.
 */
class LoginActivity : BaseActivity(), BaseView {

    @Inject lateinit var presenter: LoginPresenter
    private lateinit var loading: LVGhost

    val RESULT_OK = 1

    override fun showLoading() {
        loading = LVGhost(this)
        val params = RelativeLayout.LayoutParams(300f.dp2px().toInt(), 150f.dp2px().toInt())
        params.addRule(RelativeLayout.CENTER_IN_PARENT)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            params.topMargin = 300f.dp2px().toInt()
            loading.elevation = 4f.dp2px()
        }
        loading.layoutParams = params
        loading.startAnim()
        rl_login.addView(loading, 1)

    }

    override fun hideLoading() {
        loading.stopAnim()
        rl_login.removeView(loading)
    }

    override fun launchActivity(intent: Intent) {

    }

    override fun showMessage() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        finish()
    }

    override fun initView(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)

        login_btn.setOnClickListener {
            val username = username.text.toString().trim()
            val password = password.text.toString().trim()
            if (!TextUtils.isEmpty(username) && !TextUtils.isEmpty(password)) {
                presenter.login(username, password)

            } else {
                toast("请输入用户名和密码")
            }
        }
    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(AuthModule(this))
                .inject(this)
    }

    /**
     * 点击返回键返回桌面而不是退出程序
     */
    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            val home = Intent(Intent.ACTION_MAIN)
            home.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP
            home.addCategory(Intent.CATEGORY_HOME)
            startActivity(home)
            return true
        }
        return super.onKeyDown(keyCode, event)
    }
}