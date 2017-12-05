package com.gkzxhn.mygithub.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Event
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.extension.loadRoundConner
import com.gkzxhn.mygithub.utils.Utils.getDiffTime
import com.gkzxhn.mygithub.utils.Utils.parseDate

/**
 * Created by Xuezhi on 2017/11/19.
 */
class EventAdapter(datas: List<Event>?) : BaseQuickAdapter<Event, BaseViewHolder>(R.layout.item_notifacation, datas) {

    override fun convert(helper: BaseViewHolder?, item: Event?) {

        var did: String

        var creatTiem = parseDate(item!!.created_at, "yyyy-MM-dd'T'HH:mm:ss'Z'")

        var toNow = getDiffTime(creatTiem)

        if (item!!.type.equals("PushEvent")) {
            did = " pushed to "
            helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
            helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.push_icon) }
        } else if (item!!.type.equals("")) {

        } else {
            did = "*等我改*"
            helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
            //TODO("not implemented")//这里有几种不同的event判断，还没理清。
            //这个todo会造成程序崩溃。。。
        }

        helper!!.setText(R.id.tv_new_date_notification, toNow)

        helper!!.getView<ImageView>(R.id.iv_avatar)
                .let { it.loadRoundConner(it.context, item.actor.avatar_url) }
    }
}