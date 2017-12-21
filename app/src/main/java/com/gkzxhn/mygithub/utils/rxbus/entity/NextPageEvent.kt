package com.gkzxhn.mygithub.utils.rxbus.entity

/**
 * Created by 方 on 2017/12/20.
 */
data class NextPageEvent(
        val position: Int,
    val path: String,
    val name: String
)