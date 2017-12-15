package com.gkzxhn.mygithub.ui.adapter

import android.annotation.SuppressLint
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.bean.info.Owner
import com.gkzxhn.mygithub.bean.info.User
import com.gkzxhn.mygithub.extension.dp2px
import com.gkzxhn.mygithub.extension.load

/**
 * Created by 方 on 2017/11/2.
 */
class UserListAdapter(datas : List<Any>?) : BaseQuickAdapter<Any, BaseViewHolder>(R.layout.item_users, datas) {

    @SuppressLint("ResourceAsColor")
    override fun convert(helper: BaseViewHolder?, item: Any?) {
        helper!!.addOnClickListener(R.id.tv_follow)
        if (item is User) {
            helper!!.setText(R.id.tv_username, item.login)
            helper.setText(R.id.tv_bio, item.bio)
            helper.getView<ImageView>(R.id.iv_avatar)
                .let { it.load(it.context, item.avatar_url, R.drawable.default_avatar) }
            val tvFollow = helper.getView<TextView>(R.id.tv_follow)
            when (isFollowing[helper.layoutPosition]) {
                0 -> {
                    helper.setVisible(R.id.pb_follow, false)
                    tvFollow.visibility = View.VISIBLE
                    tvFollow.text = "Unfollow"
                    tvFollow.setBackgroundResource(R.drawable.gray_btn_back)
                    tvFollow.setTextColor(tvFollow.context.resources.getColor(R.color.text_black))
                    val leftDrawable = tvFollow.context.resources.getDrawable(R.drawable.follow)
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                    tvFollow.setCompoundDrawablePadding(3f.dp2px().toInt())//设置图片和text之间的间距
                    tvFollow.setCompoundDrawables(leftDrawable, null, null, null)
                    }
                1 -> {
                    helper.setVisible(R.id.pb_follow, false)
                    tvFollow.visibility = View.VISIBLE
                    tvFollow.text = "Follow"
                    tvFollow.setTextColor(tvFollow.context.resources.getColor(R.color.white))
                    tvFollow.setBackgroundResource(R.drawable.green_bg)
                    val leftDrawable = tvFollow.context.resources.getDrawable(R.drawable.unfollow)
                    leftDrawable.setBounds(0, 0, leftDrawable.getMinimumWidth(), leftDrawable.getMinimumHeight());
                    tvFollow.setCompoundDrawablePadding(3f.dp2px().toInt())//设置图片和text之间的间距
                    tvFollow.setCompoundDrawables(leftDrawable, null, null, null)
                }
                else -> {
                    helper.setVisible(R.id.pb_follow, true)
                    tvFollow.visibility = View.GONE
                }
            }
        }else if(item is Owner) {
            helper!!.setText(R.id.tv_username, item.login)
            helper.getView<ImageView>(R.id.iv_avatar)
                    .let { it.load(it.context, item.avatar_url, R.drawable.default_avatar) }
            if ("USER" != item.type) {
                helper.setVisible(R.id.pb_follow, false)
            }
        }else if (item is Icon2Name) {
            helper!!.setText(R.id.tv_username, item.name)
            helper.getView<ImageView>(R.id.iv_avatar)
                    .let { it.load(it.context, item.avatarUrl, R.drawable.default_avatar) }
        }else if(item is Organization) {
            helper.setVisible(R.id.pb_follow, false)
                    .setText(R.id.tv_username, item.login)
                    .setText(R.id.tv_bio, if(item.description == null) "" else item.description)
            helper.getView<ImageView>(R.id.iv_avatar)
                    .let { it.load(it.context, item.avatar_url, R.drawable.default_avatar) }
        }
    }

    var isFollowing = hashMapOf<Int, Int>()
}