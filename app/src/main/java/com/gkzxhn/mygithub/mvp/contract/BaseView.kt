package com.gkzxhn.balabala.mvp.contract

import android.content.Intent

/**
 * Created by 方 on 2017/10/19.
 */
interface BaseView {
    /**
     * 进入Activity
     */
    fun launchActivity(intent: Intent)

    /**
     * 显示加载
     */
    fun showLoading()

    /**
     * 隐藏加载
     */
    fun hideLoading()

    /**
     * 显示信息
     */
    fun showMessage()

    /**
     * 杀死自己
     */
    fun killMyself()

}