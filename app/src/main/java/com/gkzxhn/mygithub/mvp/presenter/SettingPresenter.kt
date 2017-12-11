package com.gkzxhn.mygithub.mvp.presenter

import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.bean.entity.FinishMain
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import javax.inject.Inject

/**
 * Created by Xuezhi on 2017/12/11.
 */
class SettingPresenter @Inject constructor(private val rxBus: RxBus, private val view: BaseView) {
    fun sentMessage() {
        rxBus.post(FinishMain("结束MainActivity"))
    }
}