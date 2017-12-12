package com.gkzxhn.mygithub.api

import com.gkzxhn.mygithub.bean.info.TrendingResults
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.*

/**
 * Created by 方 on 2017/11/9.
 */
interface TrendingApi {

    /**
     * @param since 可选，get请求参数，无这参数则自动获取当天的热门项目，参数值只有三个，分别是daily,weekly,monthly。
     */
    @GET("/repo")
    fun getTrendingRepos(@Query("lang") language : String = "",
                         @Query("since") since : String):Observable<TrendingResults>

    @GET("/developer")
    fun getTrendingUsers(@Query("lang") language : String = "",
                         @Query("since") since: String) : Observable<TrendingResults>

    @GET("/lang")
    fun getTrendingLang() : Observable<Objects>
}