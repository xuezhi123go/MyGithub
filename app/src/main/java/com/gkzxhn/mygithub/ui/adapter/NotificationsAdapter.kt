package com.gkzxhn.mygithub.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Notifications

/**
 * Created by Xuezhi on 2017/11/15.
 */
class NotificationsAdapter(datas: List<Notifications>?) : BaseQuickAdapter<Notifications, BaseViewHolder>(R.layout.item_notifacation, datas) {
    override fun convert(helper: BaseViewHolder?, item: Notifications?) {

        helper!!.setText(R.id.tv_new_data_notification,item!!.reason)
        //helper!!.getView<ImageView>(R.id.iv_avatar).let { it.load(it.context, item.repository.owner.avatar_url, R.drawable.default_avatar) }
    }
}