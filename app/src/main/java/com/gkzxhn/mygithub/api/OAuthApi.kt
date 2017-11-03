package com.gkzxhn.mygithub.api

import com.gkzxhn.mygithub.bean.info.*
import io.reactivex.Observable
import okhttp3.RequestBody
import retrofit2.http.*
import java.util.*

/**
 * Created by 方 on 2017/10/23.
 */

interface OAuthApi {

    /**
     * 根据用户名获得用户信息
     */
    @GET("/users/{username}")
    fun getUser(@Path("username") username: String) : Observable<User>

    /**
     * 获得我的仓库列表
     * @param visibility  all，public或private
     * @param affiliation* owner：由经过身份验证的用户拥有的存储库。
     *                     collaborator：作为协作者添加到用户的存储库。
     *                     organization_member：用户可以通过组织成员访问的存储库。这包括用户所在的每个团队的每个存储库。
     * @param type         all，owner，public，private，member
     * @param sort         created，updated，pushed，full_name
     * @param direction    asc或desc
     */
    @GET("/user/repos")
    fun getRepos(@Query("visibility") visibility: String = "all",
                 @Query("affiliation") affiliation: String = "owner,collaborator,organization_member",
//                 @Query("type")type :String = "all",
                 @Query("sort") sort: String = "full_name",
                 @Query("direction") direction: String = "asc"): Observable<List<Repo>>


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
                  @Query("state") state: String = "all",
//                  @Query("assignee")assignee :String = "none",
//                  @Query("creator")creator :String = "none",
//                  @Query("mentioned")mentioned :String = "none",
//                  @Query("labels")labels :String = "none",
                  @Query("sort") sort: String = "created",
                  @Query("direction") direction: String = "desc"
            /*@Query("since")since :String = "none"*/): Observable<List<Issue>>


    /**
     * 获取别人的Stars
     */
    @GET("/users/{username}/starred")
    fun getStars(@Path("username") username: String)
            : Observable<List<Starred>>


    @GET("/user/starred")
    fun getMyStars(
            @Query("sort") sort: String = "created",
            @Query("direction") direction: String = "desc"
            /*@Query("since")since :String = "none"*/): Observable<List<Starred>>

    /**
     * 得到issue评论列表
     */
    @GET("/repos/{owner}/{repo}/issues/{number}/comments")
    fun getComments(@Path("owner") owner: String,
                    @Path("repo") repo: String,
                    @Path("number") number: Int): Observable<List<Comment>>

    /**
     * 提交issue
     */
    @POST("/repos/{owner}/{repo}/issues")
    fun postIssue(@Path("owner") owner: String,
                  @Path("repo") repo: String,
                  @Body requestBody: RequestBody): Observable<PostIssueResponse>

    /**
     * 提交issue评论
     */
    @POST("/repos/{owner}/{repo}/issues/{number}/comments")
    fun postIssueComment(@Path("owner") owner: String,
                         @Path("repo") repo: String,
                         @Path("number") number: Int,
                         @Body requestBody: RequestBody): Observable<Comment>


    /**
     * 获取您的组织列表
     */
    @GET("/user/orgs")
    fun getOrgs():Observable<List<Organization>>

    /**
     * 获取用户的组织列表
     */
    @GET("/users/{username}/orgs")
    fun getUserOrgs(
            @Path("username") username: String
    ):Observable<List<Organization>>

    /**
     * 得到一个组织
     */
    @GET("/orgs/{org}")
    fun getOrg(
            @Path("org") org: String
    ):Observable<Organization>

    /**
     * 获取组织仓库列表
     * @param 可以是一个all，public，private，forks，sources，member。默认：all
     */
    @GET("/orgs/{org}/repos")
    fun getOrgRepos(
            @Path("org")org : String,
            @Query("type") type : String = "all"):Observable<List<Repo>>

    /**
     * 得到仓库合作者列表
     */
    @GET("repos/{owner}/{repo}/contributors")
    fun contributors(@Path("owner") owner: String,
                              @Path("repo") repo: String): Observable<ArrayList<User>>


    /**
     * 得到仓库分支列表
     * @param sort      The sort order. Can be either newest, oldest, or stargazers. Default: newest
     */
    @GET("repos/{owner}/{repo}/forks")
    fun listForks(@Path("owner") owner: String,
                  @Path("repo") repo: String,
                  @Query("sort") sort: String = "newest"): Observable<ArrayList<Repo>>
}