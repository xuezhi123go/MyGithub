package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.di.module.AuthModule
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.LoginPresenter
import kotlinx.android.synthetic.main.activity_login.*
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/19.
 */
class LoginActivity :BaseActivity(), BaseView{

    @Inject lateinit var presenter: LoginPresenter

    val RESULT_OK = 1

    override fun launchActivity(intent: Intent) {

    }

    override fun showLoading() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
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
            }else {
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
}