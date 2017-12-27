package com.gkzxhn.mygithub.api

import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET

/**
 * Created by 方 on 2017/11/9.
 */
interface ExploreApi {

    @GET("/")
    fun getExploreContent(): Observable<ResponseBody>

}