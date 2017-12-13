package com.gkzxhn.mygithub.extension

import android.annotation.SuppressLint
import android.content.Context
import android.widget.Toast

/**
 * Created by 方 on 2017/10/21.
 */

var toast :Toast? = null

@SuppressLint("ShowToast")
fun Context.toast(msg : String) {
    if (toast == null) {
        toast = Toast.makeText(this, msg, Toast.LENGTH_SHORT)
    }
    toast!!.setText(msg)
    toast!!.show()
}