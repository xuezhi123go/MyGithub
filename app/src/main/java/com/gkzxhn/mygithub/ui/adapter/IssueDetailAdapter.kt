package com.gkzxhn.mygithub.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Comment
import com.gkzxhn.mygithub.extension.load

/**
 * Created by 方 on 2017/10/26.
 */
class IssueDetailAdapter(datas :List<Comment>?): BaseQuickAdapter<Comment, BaseViewHolder>(R.layout.issue_conment, datas) {
    override fun convert(helper: BaseViewHolder?, item: Comment?) {
        helper!!.setText(R.id.tv_name, item!!.user.login)
        helper.setText(R.id.tv_body, item.body)
        helper.setText(R.id.tv_create_time, item.created_at.substring(0, item.created_at.indexOf("T")))
        helper.getView<ImageView>(R.id.img_avatar)
                .let { it.load(it.context, item.user.avatar_url, R.drawable.default_avatar) }
    }

}