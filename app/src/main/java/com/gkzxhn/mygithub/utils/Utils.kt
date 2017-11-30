package com.gkzxhn.mygithub.utils

import android.util.Log
import java.text.ParseException
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
        var curDate = Date(System.currentTimeMillis())
        return curDate
    }

    /**
     * 把获取到的时间格式化成ISO8601形式
     */
    fun getFormatTime(date: Date): String? {
        var time = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(date)
        //time = time.substring(0, 22) + ":" + time.substring(22)
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
    /**
     * 将日期字符串转换为时间
     * @param date 日期字符串，必须为"yyyy-MM-dd'T'HH:mm:ss'Z'"
     * @param format 格式化字符串
     * @return 日期字符串的Date对象表达形式
     */
    @JvmOverloads
    fun parseDate(date: String, format: String): Long {
        var dt: Date? = null
        val dateFormat = SimpleDateFormat(format)
        try {
            dt = dateFormat.parse(date)
        } catch (e: ParseException) {
            e.printStackTrace()
        }
        return dt!!.time
    }

    /**
     * 获取当前时间距离指定日期时差的大致表达形式
     * @param long date 日期
     * @return 时差的大致表达形式
     */
    fun getDiffTime(date: Long): String {
        /** 1s==1000ms  */
        val TIME_MILLISECONDS = 1000
        /** 时间中的分、秒最大值均为60  */
         val TIME_NUMBERS = 60
        /** 时间中的小时最大值  */
         val TIME_HOURSES = 24

        var strTime = "long long ago"
        val time = Math.abs(Date().time - date)
        // 一分钟以内
        if (time < TIME_NUMBERS * TIME_MILLISECONDS) {
            strTime = "just now"
        } else {
            val min = (time / TIME_MILLISECONDS.toLong() / TIME_NUMBERS.toLong()).toInt()
            if (min < TIME_NUMBERS) {
                if (min < 15) {
                    strTime = "a quarter of an hour ago"
                } else if (min < 30) {
                    strTime = "half an hour ago"
                } else {
                    strTime = "1 hour ago"
                }
            } else {
                val hh = min / TIME_NUMBERS
                if (hh < TIME_HOURSES) {
                    strTime = hh.toString() + " hour ago"
                } else {
                    val days = hh / TIME_HOURSES
                    if (days <= 6) {
                        strTime = days.toString() + " days ago"
                    } else {
                        val weeks = days / 7
                        if (weeks < 4) {
                            strTime = weeks.toString() + " weeks ago"
                        }
                    }
                }
            }
        }

        return strTime
    }
}
