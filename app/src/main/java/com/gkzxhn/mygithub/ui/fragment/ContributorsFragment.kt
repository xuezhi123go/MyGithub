package com.gkzxhn.mygithub.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.mvp.presenter.IssuePresenter
import com.gkzxhn.mygithub.ui.activity.UserActivity
import com.gkzxhn.mygithub.ui.adapter.UserListAdapter
import com.gkzxhn.mygithub.ui.wedgit.RecycleViewDivider
import kotlinx.android.synthetic.main.fragment_issue.*
import javax.inject.Inject

@SuppressLint("ValidFragment")
/**
 * Created by 方 on 2017/11/2.
 */
class ContributorsFragment constructor(private val repo: Repo?,
                                       private val type : String,
                                       private val q: String = "") : BaseView, BaseFragment() {

    @Inject lateinit var presenter:IssuePresenter
    var isLoading: Boolean = false

    private var owner : String? = null
    private var repoName: String? = null

    private lateinit var adapter: UserListAdapter

    override fun setupComponent() {
        App.getInstance()
                .baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initContentView() {

        initRecyclerView()

    }

    override fun onVisible() {
        getNewData()
    }

    private fun initRecyclerView() {
        adapter = UserListAdapter(null)
        adapter.openLoadAnimation()
        rv_issue.layoutManager = LinearLayoutManager(context)
        rv_issue.adapter = adapter
        rv_issue.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL,2,
                App.getInstance().resources.getColor(R.color.gray_back)))
        adapter.setOnItemClickListener {
            adapter, view, position ->
            val user = adapter.data[position] as Parcelable
            val intent = Intent(context, UserActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(IntentConstant.USER, user)
            intent.putExtras(bundle)
            startActivity(intent)
        }
        adapter.setOnItemChildClickListener{
            adapter, view, position ->
            when (view.id) {
                R.id.tv_follow -> {
                    val username = (adapter.getViewByPosition(rv_issue, position, R.id.tv_username) as TextView).text.toString()
                    when (this.adapter.isFollowing[position]) {
                        0 -> {
                            //已关注,点击取消关注
                            presenter.unFollowUser(position, username)
                        }
                        1 -> {
                            presenter.followUser(position, username)
                        }
                        else -> {

                        }
                    }
                }
                else -> {
                }
            }
        }
        srl_issue.setOnRefreshListener {
            getNewData()
        }
    }

    private fun getNewData() {
        owner = repo?.let {  return@let it.owner.login}
        repoName = repo?.let {  return@let it.name}
        if (type.equals(IntentConstant.CONTRIBUTORS)) {
            presenter.getContributors(owner!!, repoName!!)
        }else if(type.equals(IntentConstant.FORKS)) {
            presenter.getForks(owner!!, repoName!!)
        }else if(type.equals(IntentConstant.USERS)){
            presenter.searchUsers(q)
        }
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_issue, container, false)
    }

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        isLoading = true
        srl_issue?.let {
            it.isRefreshing = true
        }
    }

    override fun hideLoading() {
        isLoading = false
        srl_issue?.let {
            it.isRefreshing = false
        }
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun loadData(lists: List<Any>){
        if (lists.isEmpty()) {
            adapter.emptyView = LayoutInflater.from(context).inflate(R.layout.empty_view, null, false)
        }
        adapter.setNewData(lists)
    }

    fun updateList(position : Int, data : Any){
        adapter.data[position] = data
        adapter.notifyItemChanged(position, data)
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(javaClass.simpleName, "ondestroy")
    }


    /**
     * 更新follow标签状态
     * @param isFollowing 0表示已关注,1表示未关注,-1表示正在查询
     */
    fun updateListFollowStatus(position: Int, isFollowing: Int){
        adapter.isFollowing.put(position, isFollowing)
        adapter.notifyItemChanged(position)
    }
}