package com.gkzxhn.mygithub.extension

import android.app.Activity
import android.view.WindowManager
import android.widget.EditText

/**
 * Created by 方 on 2017/11/13.
 */

fun EditText.showSoftInputFromWindow(activity: Activity){
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
}