package com.gkzxhn.mygithub.api

import com.gkzxhn.mygithub.bean.info.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Created by 方 on 2017/10/23.
 */

interface OAuthApi {

    /**
     * @param visibility  all，public或private
     * @param affiliation* owner：由经过身份验证的用户拥有的存储库。
     *                     collaborator：作为协作者添加到用户的存储库。
     *                     organization_member：用户可以通过组织成员访问的存储库。这包括用户所在的每个团队的每个存储库。
     * @param type         all，owner，public，private，member
     * @param sort         created，updated，pushed，full_name
     * @param direction    asc或desc
     */
    @GET("/user/repos")
    fun getRepos(@Query("visibility")visibility :String = "all",
                 @Query("affiliation")affiliation :String = "owner,collaborator,organization_member",
                 @Query("type")type :String = "all",
                 @Query("sort")sort :String = "full_name",
                 @Query("direction")direction :String = "asc") : Observable<List<Repo>>
}