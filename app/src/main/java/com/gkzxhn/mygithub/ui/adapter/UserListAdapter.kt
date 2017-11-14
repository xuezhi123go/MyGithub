package com.gkzxhn.mygithub.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Owner
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.extension.load

/**
 * Created by 方 on 2017/11/2.
 */
class UserListAdapter(datas : List<Any>?) : BaseQuickAdapter<Any, BaseViewHolder>(R.layout.item_users, datas) {

    override fun convert(helper: BaseViewHolder?, item: Any?) {
        if (item is User) {
            helper!!.setText(R.id.tv_username, item.login)
            helper.setText(R.id.tv_bio, item.bio)
            helper.getView<ImageView>(R.id.iv_avatar)
                .let { it.load(it.context, item.avatar_url, R.drawable.default_avatar) }
        }else if(item is Owner) {
            helper!!.setText(R.id.tv_username, item.login)
            helper.getView<ImageView>(R.id.iv_avatar)
                    .let { it.load(it.context, item.avatar_url, R.drawable.default_avatar) }
        }
    }
}