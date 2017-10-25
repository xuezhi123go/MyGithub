package com.gkzxhn.balabala.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.trello.rxlifecycle2.components.support.RxFragment

/**
 * Created by 方 on 2017/10/19.
 */
abstract class BaseFragment :RxFragment(){


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = initView(inflater, container, savedInstanceState)
        setupComponent()
        return view
    }

    protected open fun setupComponent(){}

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatusBar()
        initToolBar()

        initContentView()
    }

    /**
     * 初始化内容视图
     */
    abstract fun initContentView()

    open fun getToolbar(): Toolbar? = null
    open fun getStatusBar(): View? = null

    abstract fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?) : View

    open fun initToolBar() {
        getToolbar()?.let{
            try {
                (activity as AppCompatActivity).setSupportActionBar(it)
            } catch(e: Exception) {
            }
        }
    }

    private fun initStatusBar() {
        getStatusBar()?.let {
            it.layoutParams.height = getStatusHeight(activity)
        }
    }

    fun getStatusHeight(activity: Activity): Int {
        // 获得状态栏高度
        val resourceId = activity.resources.getIdentifier("status_bar_height", "dimen", "android")
        val statusBarHeight = activity.resources.getDimensionPixelSize(resourceId)
        return statusBarHeight
    }
}