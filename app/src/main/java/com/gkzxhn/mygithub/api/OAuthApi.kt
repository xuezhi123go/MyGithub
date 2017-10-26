package com.gkzxhn.mygithub.api

import com.gkzxhn.mygithub.bean.info.Issue
import com.gkzxhn.mygithub.bean.info.Repo
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
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
//                 @Query("type")type :String = "all",
                 @Query("sort")sort :String = "full_name",
                 @Query("direction")direction :String = "asc") : Observable<List<Repo>>


    /**
     * 得到详细的仓库内容
     */
    @GET("repos/{owner}/{repo}")
    fun get(@Path("owner") owner: String, @Path("repo") repo: String): Observable<Repo>

    /**
     * 得到问题列表
     * @param milestone
     * @param state
     * @param assignee
     * @param creator
     * @param mentioned
     * @param labels    以逗号分隔的标签名称列表。例：bug,ui,@high
     * @param sort      什么排序结果。可以是created，updated，comments
     * @param direction 排序方向。可以是asc或desc。默认：desc
     * @param since     仅返回此时或之后更新的问题。这是ISO 8601格式的时间戳YYYY-MM-DDTHH:MM:SSZ。
     */
    @GET("/repos/{owner}/{repo}/issues")
    fun getIssues(@Path("owner") owner: String, @Path("repo") repo: String,
//                  @Query("milestone")milestone :String = "none",
                  @Query("state")state :String = "all",
//                  @Query("assignee")assignee :String = "none",
//                  @Query("creator")creator :String = "none",
//                  @Query("mentioned")mentioned :String = "none",
//                  @Query("labels")labels :String = "none",
                  @Query("sort")sort :String = "created",
                  @Query("direction")direction :String = "desc"
                  /*@Query("since")since :String = "none"*/): Observable<List<Issue>>
}