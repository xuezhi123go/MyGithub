package com.gkzxhn.mygithub.ui.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App
import com.gkzxhn.mygithub.bean.info.Content
import com.gkzxhn.mygithub.constant.IntentConstant
import com.gkzxhn.mygithub.di.module.OAuthModule
import com.gkzxhn.mygithub.mvp.presenter.FileListPresenter
import com.gkzxhn.mygithub.ui.activity.FileDetailActivity
import com.gkzxhn.mygithub.ui.adapter.FileListAdapter
import com.gkzxhn.mygithub.ui.wedgit.RecycleViewDivider
import kotlinx.android.synthetic.main.fragment_file_list.*
import javax.inject.Inject

@SuppressLint("ValidFragment")
/**
 * Created by 方 on 2017/11/2.
 */
class FileListFragment (val index: Int) : BaseView, BaseFragment() {

    @Inject lateinit var presenter: FileListPresenter
    var isLoading: Boolean = false

    var owner : String? = null
    var repoName: String? = null
    var path: String = ""

    private lateinit var adapter: FileListAdapter

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
        if (TextUtils.isEmpty(path)) {
            getNewData()
        }
    }

    private fun initRecyclerView() {
        adapter = FileListAdapter(null)
        adapter.openLoadAnimation()
        rv_file_list.layoutManager = LinearLayoutManager(context)
        rv_file_list.adapter = adapter
        rv_file_list.addItemDecoration(RecycleViewDivider(context, LinearLayoutManager.HORIZONTAL, 1,
                App.getInstance().resources.getColor(R.color.gray_back)))
        adapter.setOnItemClickListener {
            adapter, view, position ->
            val item = adapter.data[position]
            when ((item as Content).type) {
                "dir" -> {
                    presenter.goNextPage(index, item.path, item.name)
                }
                "file" -> {
                    val intent = Intent(context, FileDetailActivity::class.java)
                    intent.putExtra(IntentConstant.PATH, item.path)
                    intent.putExtra(IntentConstant.REPO, repoName)
                    intent.putExtra(IntentConstant.NAME, owner)
                    startActivity(intent)
                }
                else -> {
                }
            }
        }

        srl_file_list.setOnRefreshListener {
            getNewData()
        }
    }

    fun getNewData() {
        adapter.setNewData(null)
        presenter.getCode(owner!!, repoName!!, path)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_file_list, container, false)
    }

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        isLoading = true
        srl_file_list?.let {
            it.isRefreshing = true
        }
    }

    override fun hideLoading() {
        isLoading = false
        srl_file_list?.let {
            it.isRefreshing = false
        }
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun loadData(lists: List<Content>){
        if (lists.isEmpty()) {
            adapter.emptyView = LayoutInflater.from(context).inflate(R.layout.empty_view, null, false)
        }else {
            adapter.setNewData(lists.sortedBy { content -> content.type == "file" })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(javaClass.simpleName, "ondestroy")
    }
}