package com.gkzxhn.mygithub.mvp.model

import com.gkzxhn.mygithub.api.AccountApi

/**
 * Created by 方 on 2017/10/20.
 */
class LoginModel /*@Inject*/ constructor(private val api: AccountApi){

    fun login(){


    }

    fun getToken(username : String, password : String){

    }
}