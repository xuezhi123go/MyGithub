package com.gkzxhn.balabala.base

import android.app.Activity
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
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
        isInit = true
        return view
    }

    protected open fun setupComponent(){}

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initStatusBar()
        initToolBar()
        initContentView()
        Log.i(javaClass.simpleName, "onViewCreated : $userVisibleHint")
    }

    override fun onResume() {
        Log.i(javaClass.simpleName, "onResume : $userVisibleHint")
        if (userVisibleHint && isFirst) {
            onVisible()
            isFirst = false
        }
        super.onResume()
    }

    override fun onDestroy() {
        isInit = false
        super.onDestroy()
    }

    private var isFirst = true
    private var isInit = false

    override fun setUserVisibleHint(isVisibleToUser: Boolean) {
        Log.i(javaClass.simpleName, "setUserVisibleHint : ${isVisibleToUser}")
        super.setUserVisibleHint(isVisibleToUser)
        if (userVisibleHint && isInit && isFirst) {
            isFirst = false
            onVisible()
        }
    }

    open protected fun onVisible(){
        Log.i(javaClass.simpleName, "onVisible")
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