package com.gkzxhn.mygithub.mvp.presenter

import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.balabala.ui.activity.MainActivity
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.utils.rxbus.RxBus
import javax.inject.Inject

/**
 * Created by 方 on 2017/10/24.
 */
class MainPresenter @Inject constructor(private val rxBus: RxBus, private val view: BaseView){

    fun subscribe(){
        rxBus.toFlowable(User::class.java)
                .subscribe(
                        {user: User? ->
                            (view as MainActivity).toLogin(user!!)
                        }
                )
    }
}