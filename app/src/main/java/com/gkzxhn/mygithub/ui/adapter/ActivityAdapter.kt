package com.gkzxhn.mygithub.ui.adapter

import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Event
import com.gkzxhn.mygithub.constant.Constant
import com.gkzxhn.mygithub.extension.load
import com.gkzxhn.mygithub.extension.loadRoundConner
import com.gkzxhn.mygithub.utils.Utils.getDiffTime
import com.gkzxhn.mygithub.utils.Utils.parseDate

/**
 * Created by Xuezhi on 2017/11/19.
 */
class ActivityAdapter(datas: List<Event>?) : BaseQuickAdapter<Event, BaseViewHolder>(R.layout.item_notifacation, datas) {

    override fun convert(helper: BaseViewHolder?, item: Event?) {

        var lastTime = Constant.TIME

        Log.i(javaClass.simpleName, "" + lastTime)

        var did: String

        var creatTiem = parseDate(item!!.created_at, "yyyy-MM-dd'T'HH:mm:ss'Z'") + 8 * 60 * 60 * 1000

        /*这里拿到的时间是世界标准时间，所以要加上8*60*60*1000转化成中国东八区的时间*/
        var toNow = getDiffTime(creatTiem)

        when (item!!.type) {
            "PushEvent" -> {
                did = " pushed to "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.push_icon) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "WatchEvent" -> {
                did = " Starred "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.stared_icon) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "MemberEvent" -> {
                did = " added member to "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.company) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "PullRequestEvent" -> {
                did = " opened pull request "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.open_icon) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "DeleteEvent" -> {
                did = " delete branch at "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.close_icon) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "IssueCommentEvent" -> {
                did = " created comment on issue in "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
                helper!!.setText(R.id.tv_detail_data_notification, item!!.payload.comment.body)
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.VISIBLE }
            }
            "IssuesEvent" -> {
                did = " created issus in "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
                helper!!.setText(R.id.tv_detail_data_notification, item!!.payload.issue.title)
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.VISIBLE }
            }
            "ForkEvent" -> {
                did = " forked "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.open_icon) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "PullRequestReviewCommentEvent" -> {
                did = " pull request review comment in "
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + did + item!!.repo.name)
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            "PublicEvent" -> {
                helper!!.setText(R.id.tv_new_data_notification, item!!.actor.login + " leted" + item!!.repo.name + " public")
                helper!!.getView<ImageView>(R.id.iv_state_icon).let { it.load(it.context, R.drawable.public_activity) }
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
            else -> {
                helper!!.setText(R.id.tv_new_data_notification, " 请提交log给开发小哥确定Event类型 O(∩_∩)O ")
                helper.getView<TextView>(R.id.tv_detail_data_notification).let { it.visibility = View.GONE }
            }
        }
        helper!!.setText(R.id.tv_new_date_notification, toNow)
        helper!!.getView<ImageView>(R.id.iv_avatar)
                .let { it.loadRoundConner(it.context, item.actor.avatar_url) }
    }
}