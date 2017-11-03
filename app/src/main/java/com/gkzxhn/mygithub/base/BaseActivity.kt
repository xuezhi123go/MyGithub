package com.gkzxhn.balabala.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.widget.Toolbar
import android.view.View
import com.gkzxhn.mygithub.R
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
        getToolbar()?.let {
            if (canBack) {
                it.setNavigationIcon(R.drawable.ic_arrow_back)
            }
        }
        initStatusBar()
    }

    open fun setupComponent() {

    }

    open fun getToolbar(): Toolbar? = null
    open fun getStatusBar(): View? = null

    abstract fun initView(savedInstanceState: Bundle?)

    private var listener : Toolbar.OnMenuItemClickListener? = null

    fun setToolbarMenuClickListener(listener: Toolbar.OnMenuItemClickListener?){this.listener = listener!!}

    open fun initToolBar() {
        getToolbar()?.let{
            setSupportActionBar(it)
            it.setOnMenuItemClickListener(listener)
            it.setNavigationOnClickListener { finish() }
        }
    }

    private var canBack = false

    /**
     * 设置ToolBar是否带返回键
     */
    fun setToolBarBack(canBack : Boolean){
        this.canBack = canBack
    }

    private fun initStatusBar() {
        getStatusBar()?.let {
            it.layoutParams.height = getStatusHeight(this)
        }
    }

    // 获得状态栏高度
    fun getStatusHeight(activity: Activity): Int {
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