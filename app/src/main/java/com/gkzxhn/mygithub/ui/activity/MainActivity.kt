package com.gkzxhn.balabala.ui.activity

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.text.TextUtils
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import com.gkzxhn.balabala.base.BaseActivity
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.R.id.*
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.di.module.AuthModule
import com.gkzxhn.mygithub.extension.edit
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.extension.toast
import com.gkzxhn.mygithub.mvp.presenter.MainPresenter
import com.gkzxhn.mygithub.ui.activity.LoginActivity
import com.gkzxhn.mygithub.ui.fragment.HomeFragment
import com.gkzxhn.mygithub.ui.fragment.ProfileFragment
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import io.reactivex.Flowable
import kotlinx.android.synthetic.main.activity_main.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/19.
 */
class MainActivity : BaseActivity(), BaseView {
    @Inject
    lateinit var mainPresenter: MainPresenter

    override fun launchActivity(intent: Intent) {
        startActivity(intent)
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
//        App.getMInstance()
//                .baseComponent
//                .plus(ApiModule(this))
//                .inject(this)

        setContentView(R.layout.activity_main)
        initFragments()
        setBottomBar()
        setDrawer()

        mainPresenter.subscribe()

    }

    private lateinit var text_username:TextView
    private lateinit var img_avatar:ImageView

    private val LOGIN_REQUEST = 1000
    private val RESULT_OK = 1

    private fun setDrawer() {
        val headerView = navigation.getHeaderView(0)
        text_username = headerView.findViewById<TextView>(R.id.text_username)
        img_avatar = headerView.findViewById<ImageView>(R.id.img_avatar)
        val access_token = SharedPreConstant.USER_SP
                .getSharedPreference()
                .getString(SharedPreConstant.ACCESS_TOKEN, "")
        if (!TextUtils.isEmpty(access_token)) {
            val avatar = SharedPreConstant.USER_SP
                    .getSharedPreference()
                    .getString(SharedPreConstant.AVATAR_URL, "")
            img_avatar.load(this, avatar, R.drawable.default_avatar)
            text_username.text = SharedPreConstant.USER_SP
                    .getSharedPreference()
                    .getString(SharedPreConstant.USER_NAME, "")
        }
        img_avatar.setOnClickListener {
            if (TextUtils.isEmpty(access_token)) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }

        setDrawerItemClick()
    }

    /**
     * 设置侧边栏点击事件
     */
    private fun setDrawerItemClick() {
        navigation.setNavigationItemSelectedListener {
            item ->
            when(item.itemId){
                R.id.drawer_logout ->
                    SharedPreConstant.USER_SP.getSharedPreference()
                            .edit {
                                remove(SharedPreConstant.ACCESS_TOKEN)
                            }
            }
            true
        }

    }

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(AuthModule(this))
                .inject(this)
    }

    private fun setBottomBar() {
        bottom_navigation.setOnNavigationItemSelectedListener {
            item ->
            val transaction = supportFragmentManager.beginTransaction()
            when (item.itemId) {
                navigation_home -> {
                    if (!mFragments[0].isAdded) {
                        transaction.add(R.id.main_content, mFragments.get(0))
                    }
                    transaction.show(mFragments[0])
                            .hide(mFragments[1])
                            .hide(mFragments[2])
                            .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                navigation_category -> {
                    if (!mFragments[1].isAdded) {
                        transaction.add(R.id.main_content, mFragments.get(1))
                    }
                    transaction.show(mFragments[1])
                            .hide(mFragments[0])
                            .hide(mFragments[2])
                            .commit()
                    return@setOnNavigationItemSelectedListener true
                }
                navigation_profile -> {
                    if (!mFragments[2].isAdded) {
                        transaction.add(R.id.main_content, mFragments.get(2))
                    }
                    transaction.show(mFragments[2])
                            .hide(mFragments[1])
                            .hide(mFragments[0])
                            .commit()
                    return@setOnNavigationItemSelectedListener true
                }

            }

            false
        }
        bottom_navigation.selectedItemId = navigation_home
    }

    lateinit var mFragments: ArrayList<Fragment>

    private fun initFragments() {
        mFragments = ArrayList()
        mFragments.add(HomeFragment())
        mFragments.add(HomeFragment())
        mFragments.add(ProfileFragment())
    }

    fun toLogin(user: User) {
        val avatar = user.avatar_url
        val name = user.login
        img_avatar.load(this, avatar, R.drawable.default_avatar)
        img_avatar.isClickable = false
        text_username.text = name
    }

    var back = 0

    override fun onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START)
            return
        }
        back++
        if (back == 2){
            finish()
        }else {
            toast("再按一次退出")
        }
        Flowable.timer(2000, TimeUnit.MILLISECONDS)
                .bindToLifecycle(this)
                .subscribe {
                    t: Long? ->
                    Log.i(javaClass.simpleName, "long :${t}")
                    back = 0
                }
    }
}