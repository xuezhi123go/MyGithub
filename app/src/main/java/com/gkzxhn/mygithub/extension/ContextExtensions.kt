package com.gkzxhn.mygithub.extension

import android.content.Context
import android.widget.Toast

/**
 * Created by 方 on 2017/10/21.
 */

fun Context.toast(msg : String)=Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()