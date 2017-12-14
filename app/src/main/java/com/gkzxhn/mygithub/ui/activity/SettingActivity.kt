package com.gkzxhn.mygithub.ui.activity

import android.content.Intent
import android.os.Bundle
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.extension.edit
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.SettingPresenter
import kotlinx.android.synthetic.main.activity_setting.*
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/12/8.
 */
class SettingActivity : BaseActivity(), BaseView {

    @Inject lateinit var settingPresenter:SettingPresenter

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

    override fun initView(savedInstanceState: Bundle?) {

        setContentView(R.layout.activity_setting)

        setClickListener()
    }

    private fun setClickListener() {

        tv_logout.setOnClickListener{
            SharedPreConstant.USER_SP.getSharedPreference()
                    .edit {
                        remove(SharedPreConstant.ACCESS_TOKEN)
                        //remove(SharedPreConstant.USER_NAME)
                        putString(SharedPreConstant.USER_NAME,"")
                    }
            intent = Intent(this,LoginActivity::class.java)
            startActivity(intent)
            //settingPresenter.sentMessage()
            finish()
        }
        tv_change_avatar.setOnClickListener {
            this.toast("修改头像")
        }
        tv_change_name.setOnClickListener {
            this.toast("修改名字")
        }
        tv_history.setOnClickListener {
            this.toast("历史记录")
        }

    }

}