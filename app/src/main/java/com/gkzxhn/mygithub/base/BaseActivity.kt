package com.gkzxhn.balabala.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.gkzxhn.mygithub.constant.SharedPreConstant
import com.gkzxhn.mygithub.extension.edit
import com.gkzxhn.mygithub.extension.getSharedPreference
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity

/**
 * Created by 方 on 2017/10/19.
 */
abstract class BaseActivity: RxAppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setupComponent()
        initView(savedInstanceState)
        initToolBar()
        initStatusBar()
    }

    open fun setupComponent() {

    }

    open fun getToolbar(): Toolbar? = null
    open fun getStatusBar(): View? = null

    abstract fun initView(savedInstanceState: Bundle?)

    open fun initToolBar() {
        getToolbar()?.let{
            setSupportActionBar(it)
        }
    }

    private fun initStatusBar() {
        getStatusBar()?.let {
            it.layoutParams.height = getStatusHeight(this)
        }
    }

    fun getStatusHeight(activity: Activity): Int {
        // 获得状态栏高度
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
        return statusBarHeight
    }

    fun putUserSp(key : String, value: Any){
        val user_sp = SharedPreConstant.USER_SP
                .getSharedPreference()
                .edit {
                    if (value is String) {
                        putString(key, value)
                    }else if (value is Int) {
                        putInt(key, value)
                    }else if (value is Boolean) {
                        putBoolean(key, value)
                    }else if (value is Float) {
                        putFloat(key, value)
                    }else if (value is Long) {
                        putLong(key, value)
                    }
                }
    }
}