package com.gkzxhn.mygithub.extension

import android.app.Activity
import android.content.Context
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.EditText

/**
 * Created by 方 on 2017/11/13.
 */

fun EditText.showSoftInputFromWindow(activity: Activity){
    this.isFocusable = true
    this.isFocusableInTouchMode = true
    this.requestFocus()
    activity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);
}

fun EditText.hideSoftInput(activity: Activity) {
    (activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
            .hideSoftInputFromWindow(this.windowToken, 0)
}