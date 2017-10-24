package com.gkzxhn.mygithub.api

import com.gkzxhn.mygithub.bean.info.AuthorizationResp
import com.gkzxhn.mygithub.bean.info.CreateAuthorization
import com.gkzxhn.mygithub.bean.info.User
import io.reactivex.Observable
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by 方 on 2017/10/23.
 */
interface AccountApi {

    @POST("/authorizations")
    fun createAuthorization(
            @Body createAuthorization: CreateAuthorization): Observable<AuthorizationResp>

    @GET("/user")
    fun getUserInfo(@Query("access_token") accessToken: String): Observable<User>
}