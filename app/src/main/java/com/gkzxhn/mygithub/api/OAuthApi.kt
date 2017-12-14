package com.gkzxhn.mygithub.api

import com.gkzxhn.mygithub.bean.info.*
import io.reactivex.Observable
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Response
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
    fun getUser(@Path("username") username: String): Observable<User>

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
//               @Query("type")type :String = "all",
                 @Query("sort") sort: String = "full_name",
                 @Query("direction") direction: String = "asc"): Observable<List<Repo>>

    /**
     * 获取用户的仓库列表
     * @param type 可以是一个all，owner，member。默认：owner
     * @param sort 可以是一个created，updated，pushed，full_name。默认：full_name
     * @param direction 可以是一个asc或desc。默认：使用时full_name：asc否则desc
     */
    @GET("/users/{username}/repos")
    fun getUserRepos(
            @Path("username") username: String,
            @Query("type") type: String = "owner",
            @Query("sort") sort: String = "created",
            @Query("direction") direction: String = "desc"
    ): Observable<List<Repo>>

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


    /**
     * 得到我的Stars
     */
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
    fun getOrgs(): Observable<List<Organization>>

    /**
     * 获取用户的组织列表
     */
    @GET("/users/{username}/orgs")
    fun getUserOrgs(
            @Path("username") username: String
    ): Observable<List<Organization>>

    /**
     * 得到一个组织
     */
    @GET("/orgs/{org}")
    fun getOrg(
            @Path("org") org: String
    ): Observable<Organization>

    /**
     * 获取组织仓库列表
     * @param 可以是一个all，public，private，forks，sources，member。默认：all
     */
    @GET("/orgs/{org}/repos")
    fun getOrgRepos(
            @Path("org") org: String,
            @Query("type") type: String = "all"): Observable<List<Repo>>

    /**
     * 得到仓库合作者列表
     */
    @GET("repos/{owner}/{repo}/contributors")
    fun contributors(@Path("owner") owner: String,
                     @Path("repo") repo: String): Observable<ArrayList<Owner>>


    /**
     * 得到仓库分支列表
     * @param sort      The sort order. Can be either newest, oldest, or stargazers. Default: newest
     */
    @GET("repos/{owner}/{repo}/forks")
    fun listForks(@Path("owner") owner: String,
                  @Path("repo") repo: String,
                  @Query("sort") sort: String = "newest"): Observable<ArrayList<Repo>>

    /**
     * 通过各种标准搜索用户,每页最多返回100个结果
     * @param q 各种搜索条件
     * @param sort 排序字段。可能是followers，repositories或joined。默认值：结果按最佳匹配排序。
     * @param order sort提供参数的排序顺序。其中之一asc或desc。默认：desc
     */
    @GET("/search/users")
    fun searchUsers(@Query("q") condition: String,
                    @Query("sort") sort: String = "followers",
                    @Query("order") order: String = "desc"): Observable<SearchUserResult>

    /**
     * 搜索仓库
     * @param q 各种搜索条件
     * @param sort 排序字段。可能是stars，forks或updated。默认值：结果按最佳匹配排序。
     * @param order sort提供参数的排序顺序。其中之一asc或desc。默认：desc
     */
    @GET("/search/repositories")
    fun searchRepos(@Query("q") condition: String,
                    @Query("sort") sort: String = "stars",
                    @Query("order") order: String = "desc"): Observable<SearchRepoResult>

    /**
     * 已订阅status : 204
     * 未订阅status : 404
     */
    @GET("/user/subscriptions/{owner}/{repo}")
    fun checkIfWatched(@Path("owner") owner: String,
                       @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * watch仓库
     */
    @Headers("Content-Length: 0")
    @PUT("/user/subscriptions/{owner}/{repo}")
    fun watchRepo(@Path("owner") owner: String
                  , @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * unwatch仓库
     */
    @DELETE("/user/subscriptions/{owner}/{repo}")
    fun unwatchRepo(@Path("owner") owner: String
                    , @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * 有星status : 204
     * 没星status : 404
     */
    @GET("/user/starred/{owner}/{repo}")
    fun checkIfStarred(@Path("owner") owner: String,
                       @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * star仓库
     */
    @Headers("Content-Length: 0")
    @PUT("/user/starred/{owner}/{repo}")
    fun starRepo(@Path("owner") owner: String
                 , @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * unStar仓库
     */
    @DELETE("/user/starred/{owner}/{repo}")
    fun unstarRepo(@Path("owner") owner: String
                   , @Path("repo") repo: String): Observable<Response<ResponseBody>>

    /**
     * 获取readme
     */
    @Headers("Cache-Control: public, max-age=3600")
    @GET("repos/{owner}/{name}/readme")
    fun getReadme(@Path("owner") owner: String, @Path("name") repo: String): Observable<Content>

    @GET("/users/{user}/following")
    fun getUserFollowing(@Path("user") user: String): Observable<ArrayList<Owner>>

    @GET("/users/{user}/followers")
    fun getUserFollowers(@Path("user") user: String): Observable<ArrayList<Owner>>

    @GET("/user/following")
    fun getMyFollowing(): Observable<ArrayList<Owner>>

    @GET("/user/followers")
    fun getMyFollowers(): Observable<ArrayList<Owner>>

    /**
     * 关注用户
     */
    @Headers("Content-Length: 0")
    @PUT("/user/following/{username}")
    fun followUser(@Path("username") username: String): Observable<Response<ResponseBody>>

    /**
     * 取消关注用户
     */
    @DELETE("/user/following/{username}")
    fun unFollowUser(@Path("username") username: String): Observable<Response<ResponseBody>>

    /**
     * 检查是否关注用户
     */
    @GET("/user/following/{username}")
    fun checkIfFollowUser(@Path("username") username: String): Observable<Response<ResponseBody>>


    /**
     * 列出你的通知
     * @param all 如果是true,显示标记为已读的通知，默认是false
     * @param participating 如果是true,只显示用户直接参与或提及的通知,默认是false
     * @param since 只显示在给定时间后更新的通知。这是 ISO 8601 格式的时间戳:YYYY-MM-DDTHH:MM:SSZ，默认是Time.now
     * @param before 只显示在给定时间之前更新的通知。这是 ISO 8601 格式的时间戳:YYYY-MM-DDTHH:MM:SSZ
     */
    @GET("/notifications")
    fun getNotifications(
            @Query("all") all: Boolean = false,
            @Query("participating") participating: Boolean = false,
            @Query("since") since: String,
            @Query("before") before: String
    ): Observable<List<Notifications>>


    /**
     * 在存储库中列出你的通知
     */
    @GET("/repos/{owner}/{repo}/notifications")
    fun getNotificationsInRepository(
            @Path("owner") owner: String,
            @Path("repo") repo: String,
            @Query("all") all: Boolean = false,
            @Query("participating") participating: Boolean = false,
            @Query("since") since: String,
            @Query("before") before: String
    ): Observable<List<Notifications>>

    /**
     *列出用户已接收的事件
     */
    @GET("/users/{username}/received_events")
    fun getEventsThatAUserHasReceived(
            @Path("username") username: String
    ): Observable<List<Event>>

    /**
     *列出用户执行的事件
     *List events performed by a user
     */
    @GET("/users/{username}/events")
    fun getEventsThatAUserPerformed(
            @Path("username") username: String
    ): Observable<List<Event>>
}