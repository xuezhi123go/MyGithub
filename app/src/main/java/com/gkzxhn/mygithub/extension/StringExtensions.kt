package com.gkzxhn.mygithub.extension

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Base64
import com.gkzxhn.mygithub.base.App

/**
 * Created by 方 on 2017/10/20.
 */
fun String.getSharedPreference(): SharedPreferences = App.getInstance().getSharedPreferences(this, MODE_PRIVATE)

fun String.base64Decode(): String = String(Base64.decode(this, 0))