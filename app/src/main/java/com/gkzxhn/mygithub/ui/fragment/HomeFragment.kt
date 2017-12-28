package com.gkzxhn.mygithub.ui.fragment

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.bean.info.*
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.mvp.presenter.HomePresenter
import com.gkzxhn.mygithub.ui.activity.*
import com.gkzxhn.mygithub.ui.adapter.AvatarListAdapter
import com.youth.banner.BannerConfig
import com.youth.banner.Transformer
import com.youth.banner.loader.ImageLoader
import kotlinx.android.synthetic.main.fragment_home.*
import javax.inject.Inject


/**
 * Created by 方 on 2017/10/19.
 */
class HomeFragment : BaseFragment(), BaseView {

    private var trendingRepoList = arrayListOf<ItemBean>()
    private var popRepoList = arrayListOf<Repo>()

    private lateinit var repoWeekAdapter: AvatarListAdapter
    private lateinit var popRepoAdapter: AvatarListAdapter
    private lateinit var popUsersAdapter: AvatarListAdapter
    @Inject lateinit var presenter: HomePresenter

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initContentView() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        setClickListener()
        setRecyclerView()
        presenter.getPopularUser()
        presenter.getPopularRepos()
//        presenter.getTrendingUser()
        presenter.getTrendingRepo()
        presenter.getBanner()
    }

    private fun setRecyclerView() {
        rv_repo_week.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        repoWeekAdapter = AvatarListAdapter(null)
        rv_repo_week.adapter = repoWeekAdapter

        rv_pop_repo.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popRepoAdapter = AvatarListAdapter(null)
        rv_pop_repo.adapter = popRepoAdapter

        rv_pop_users.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        popUsersAdapter = AvatarListAdapter(null)
        rv_pop_users.adapter = popUsersAdapter
    }

    private fun setClickListener() {
        ll_search.setOnClickListener {
            startActivity(Intent(context, SearchActivity::class.java))
        }

        ll_see_all1.setOnClickListener {
            val data = Bundle()
            data.putParcelableArrayList(IntentConstant.REPO_ENTITIES, trendingRepoList)
            val intent = Intent(context, RepoListActivity::class.java)
            intent.putExtra(IntentConstant.TOOLBAR_TITLE, "仓库周榜")
            intent.putExtras(data)
            intent.action = IntentConstant.TRENDING_REPO
            startActivity(intent)
        }

        ll_see_all2.setOnClickListener {
            val data = Bundle()
            data.putParcelableArrayList(IntentConstant.REPO_ENTITIES, popRepoList)
            val intent = Intent(context, RepoListActivity::class.java)
            intent.putExtra(IntentConstant.TOOLBAR_TITLE, "仓库流行榜")
            intent.putExtras(data)
            intent.action = IntentConstant.POP_REPO
            startActivity(intent)
        }

        ll_see_all3.setOnClickListener {
            val data = Bundle()
            data.putParcelableArrayList(IntentConstant.USERS, popUsersAdapter.data as ArrayList)
            val intent = Intent(context, RepoListActivity::class.java)
            intent.putExtras(data)
            intent.putExtra(IntentConstant.TOOLBAR_TITLE, "大牛榜")
            intent.action = IntentConstant.USERS
            startActivity(intent)
        }
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun setupComponent() {
        val baseComponent = App.getInstance()
                .baseComponent
        baseComponent
                .plus(OAuthModule(this))
                .inject(this)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    fun loadRepoWeek(result: TrendingResults) {
        trendingRepoList = result.items as ArrayList<ItemBean>
        val list = result.items
                .map { trendingItem ->
                    return@map Icon2Name(trendingItem.avatars[0].replace("s=40", "s=80"),
                            trendingItem.repo.let { return@let it.substring(it.indexOf("/") + 1) },
                            "repo")
                }
        repoWeekAdapter.setNewData(list)
        repoWeekAdapter.setOnItemClickListener { adapter, view, position ->
            val fullName = result.items[position].repo
            val intent = Intent(context, RepoDetailActivity::class.java)
            intent.putExtra(IntentConstant.FULL_NAME, fullName)
            startActivity(intent)
        }
    }

    fun loadPopUsers(result: SearchUserResult) {
        val list = result.items
                .map { item ->
                    return@map Icon2Name(item.avatar_url, item.login, "user")
                }
        popUsersAdapter.setNewData(list)
        popUsersAdapter.setOnItemClickListener { adapter, view, position ->
            val user = adapter.data[position] as Parcelable
            val intent = Intent(context, UserActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(IntentConstant.USER, user)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun loadPopRepos(result: SearchRepoResult) {
        popRepoList = result.items
        val list = result.items
                .map { item ->
                    return@map Icon2Name(item.owner.avatar_url, item.name, "user")
                }
        popRepoAdapter.setNewData(list)
        popRepoAdapter.setOnItemClickListener { adapter, view, position ->
            val repo = result.items[position]
            val intent = Intent(context, RepoDetailActivity::class.java)
            val bundle = Bundle()
            bundle.putParcelable(IntentConstant.REPO, repo)
            intent.putExtras(bundle)
            startActivity(intent)
        }
    }

    fun setBanner(srcList: List<Icon2Name>) {
        //设置banner样式
        banner.setBannerStyle(BannerConfig.CIRCLE_INDICATOR_TITLE)
        //设置图片加载器
        banner.setImageLoader(GlideImageLoader())
        //设置图片集合
        banner.setImages(srcList)
        //设置banner动画效果
        banner.setBannerAnimation(Transformer.ScaleInOut)
        //设置标题集合（当banner样式有显示title时）
        banner.setBannerTitles(srcList.map { it.name })
        //设置自动轮播，默认为true
        banner.isAutoPlay(true)
        //设置轮播时间
        banner.setDelayTime(4000)
        //设置指示器位置（当banner模式中有指示器时）
        banner.setIndicatorGravity(BannerConfig.CENTER)
        //banner设置方法全部调用完毕时最后调用
        banner.start()
    }
}

class GlideImageLoader : ImageLoader() {
    override fun displayImage(context: Context, path: Any, imageView: ImageView) {
        /**
        注意：
        1.图片加载器由自己选择，这里不限制，只是提供几种使用方法
        2.返回的图片路径为Object类型，由于不能确定你到底使用的那种图片加载器，
        传输的到的是什么格式，那么这种就使用Object接收和返回，你只需要强转成你传输的类型就行，
        切记不要胡乱强转！
         */

        imageView.load(context, (path as Icon2Name).avatarUrl!!)
        imageView.setOnClickListener {
            val intent = Intent(context, WebViewActivity::class.java)
            intent.putExtra(IntentConstant.LINK, path.type)
            intent.putExtra(IntentConstant.TITLE, path.name)
            context.startActivity(intent)
        }
    }

}