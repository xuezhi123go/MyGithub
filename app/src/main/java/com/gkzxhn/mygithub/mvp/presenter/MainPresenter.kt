package com.gkzxhn.mygithub.mvp.presenter

import android.util.Log
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.balabala.ui.activity.MainActivity
import com.gkzxhn.mygithub.bean.entity.FinishMain
import com.gkzxhn.mygithub.bean.info.Event
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.utils.AppUtils
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import com.iflytek.autoupdate.IFlytekUpdate
import com.iflytek.autoupdate.UpdateConstants
import com.iflytek.autoupdate.UpdateErrorCode
import com.trello.rxlifecycle2.kotlin.bindToLifecycle
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/24.
 */
class MainPresenter @Inject constructor(private val rxBus: RxBus, private val view: BaseView) {

    fun subscribe() {
        rxBus.toFlowable(User::class.java)
                .bindToLifecycle(view as MainActivity)
                .subscribe(
                        { user: User? ->
                            view.toLogin(user!!)
                        }
                )
        rxBus.toFlowable(Event::class.java)
                .bindToLifecycle(view as MainActivity)
                .subscribe({ event: Event? ->
                    Log.i(javaClass.simpleName, "小红点的消息")

                    view.showRedPoint()
                })
        rxBus.toFlowable(FinishMain::class.java)
                .subscribe({
                    view.finish()
                }
                )
    }


    //初始化自动更新对象
    private lateinit var updManager: IFlytekUpdate

    fun initAutoUpdate() {
        updManager = IFlytekUpdate.getInstance(view as MainActivity)
        //开启调试模式，默认不开启
//        updManager.setDebugMode(true)
        //开启wifi环境下检测更新，仅对自动更新有效，强制更新则生效
        updManager.setParameter(UpdateConstants.EXTRA_WIFIONLY, "true")
        //设置通知栏使用应用icon，详情请见示例
        updManager.setParameter(UpdateConstants.EXTRA_NOTI_ICON, "true")
        //设置更新提示类型，默认为通知栏提示
        updManager.setParameter(UpdateConstants.EXTRA_STYLE, UpdateConstants.UPDATE_UI_DIALOG)
//        updManager.setParameter(UpdateConstants.EXTRA_STYLE, UpdateConstants.UPDATE_UI_NITIFICATION);
        // 启动自动更新
        updManager.autoUpdate(view, { errorCode, updateInfo ->
            Log.i(javaClass.simpleName, "errorCode $errorCode")
            if (errorCode == UpdateErrorCode.OK && updateInfo != null) {
                Log.i(javaClass.simpleName, "updateInfo Url ${updateInfo.downloadUrl}")
                Log.i(javaClass.simpleName, "updateInfo Version ${updateInfo.updateVersionCode}")
                val versionCode = AppUtils.getVersionCode(view)
                if (updateInfo.updateVersionCode == versionCode.toString()) {
                    return@autoUpdate
                }
                updManager.showUpdateInfo(view, updateInfo)
            }
        })
    }

}