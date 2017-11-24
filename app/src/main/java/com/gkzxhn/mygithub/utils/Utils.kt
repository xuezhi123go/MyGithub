package com.gkzxhn.mygithub.utils

import android.util.Log
import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by Xuezhi on 2017/11/16.
 */
object Utils {

    /**
     * 获取当前系统时间
     */
    fun getTiem(): Date {
        //var formatter = SimpleDateFormat("YYYY-MM-DDTHH:MM:SSZ")
        var curDate = Date(System.currentTimeMillis())
        //var str = formatter.format(curDate)

        return curDate
    }

    /**
     * 格式化获取到的时间
     */
    fun getFormatTime(date: Date): String? {

        var time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ").format(date)
        time = time.substring(0, 22) + ":" + time.substring(22)
        Log.i(javaClass.simpleName, time)
        return time
    }

    /**
     * 获取i个月的时间
     */
    fun getDateBeforeOneMonth(i: Int): Date? {
        var ca = Calendar.getInstance()
        ca.add(Calendar.MONTH, i)
        var lastMonth = ca.time
        return lastMonth
    }
}
