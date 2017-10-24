package com.gkzxhn.mygithub.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Repo

/**
 * Created by 方 on 2017/10/24.
 */
class RepoListAdapter(datas: List<Repo>?) : BaseQuickAdapter<Repo, BaseViewHolder>(R.layout.item_repo, datas) {


    override fun convert(helper: BaseViewHolder?, item: Repo?) {
        helper!!.setText(R.id.tv_name, item!!.name)
        helper.setText(R.id.tv_desc, item.description)
        helper.setText(R.id.tv_create, item.created_at.substring(0, item.created_at.indexOf("T")))
    }
}