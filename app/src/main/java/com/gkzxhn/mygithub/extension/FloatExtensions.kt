package com.gkzxhn.mygithub.extension

import android.util.TypedValue
import com.gkzxhn.mygithub.base.App

fun Float.isPercentageNumber() = this >= 0f && this <= 1f

fun Float.dp2px(): Float {
    val r = App.getInstance().resources
    return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, this, r.displayMetrics)
}