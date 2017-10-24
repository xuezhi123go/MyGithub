package com.gkzxhn.mygithub.extension

import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.gkzxhn.mygithub.base.App

/**
 * Created by 方 on 2017/10/20.
 */
fun String.getSharedPreference(): SharedPreferences = App.getInstance().getSharedPreferences(this, MODE_PRIVATE)