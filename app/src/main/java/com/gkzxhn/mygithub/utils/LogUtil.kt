package com.gkzxhn.mygithub.utils

import android.util.Log



/**
 * Created by Xuezhi on 2017/12/14.
 */
object LogUtil {

    //可以全局控制是否打印log日志
    private val isPrintLog = true
    private val LOG_MAXLENGTH = 2000


    fun v(msg: String) {
        v("LogUtil", msg)
    }

    fun v(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.v(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.v(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
    }

    fun d(msg: String) {
        d("LogUtil", msg)
    }

    fun d(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.d(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.d(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
    }

    fun i(msg: String) {
        i("LogUtil", msg)
    }

    fun i(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.i(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.i(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
    }

    fun w(msg: String) {
        w("LogUtil", msg)
    }

    fun w(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.w(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.w(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
    }

    fun e(msg: String) {
        e("LogUtil", msg)
    }

    fun e(tagName: String, msg: String) {
        if (isPrintLog) {
            val strLength = msg.length
            var start = 0
            var end = LOG_MAXLENGTH
            for (i in 0..99) {
                if (strLength > end) {
                    Log.e(tagName + i, msg.substring(start, end))
                    start = end
                    end = end + LOG_MAXLENGTH
                } else {
                    Log.e(tagName + i, msg.substring(start, strLength))
                    break
                }
            }
        }
    }

}