package com.gkzxhn.mygithub.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Issue

/**
 * Created by 方 on 2017/10/25.
 */
class IssueAdapter(datas : List<Issue>?): BaseQuickAdapter<Issue, BaseViewHolder>(R.layout.item_issue, datas) {
    override fun convert(helper: BaseViewHolder?, item: Issue?) {
        helper!!.setText(R.id.tv_issue, item!!.title)
                .setText(R.id.tv_create_time, item.created_at.substring(0, 10))
                .setText(R.id.tv_issue_name, item.user.login)
                .setText(R.id.tv_is_open, item.state)
                .setText(R.id.tv_number, item.number.toString())
                .setBackgroundRes(R.id.tv_is_open, if ("open".equals(item.state)) R.drawable.green_bg else R.drawable.red_bg)
    }
}