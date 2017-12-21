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

        /**
         * 这里拿到的时间是世界标准时间，所以要加上8*60*60*1000转化成中国东八区的时间
         */
        var toNow = getDiffTime(creatTiem + 8 * 60 * 60 * 1000)

        when (item!!.type) {
            "PushEvent" -> {
                did = " pushed to "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.push_icon) }
            }
            "WatchEvent" -> {
                did = " Starred "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.stared_icon) }
            }
            "MemberEvent" -> {
                did = " added member to "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.company) }
            }
            "PullRequestEvent" -> {
                did = " opened pull request "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.open_icon) }
            }
            "DeleteEvent" -> {
                did = " delete branch at "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.close_icon) }
            }
            "IssueCommentEvent" -> {
                did = " created comment on issue in "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
            }
            "IssuesEvent" -> {
                did = " created issus in"
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
            }
            "ForkEvent" -> {
                did = " forked "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.open_icon) }
            }
            "PullRequestReviewCommentEvent" -> {
                did = " pull request review comment in "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
            }
            else -> {
                helper!!.setText(R.id.tv_new_data_notification, " 请提交log给开发小哥确定Event类型 O(∩_∩)O ")
            }
        }
        helper!!.setText(R.id.tv_new_date_notification, toNow)
        helper!!.getView<ImageView>(R.id.iv_avatar)
                .let { it.loadRoundConner(it.context, item.actor.avatar_url) }
    }
}