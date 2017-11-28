package com.gkzxhn.mygithub.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.entity.Icon2Name
import com.gkzxhn.mygithub.extension.loadRoundConner

/**
 * Created by 方 on 2017/10/27.
 */
class AvatarListAdapter(datas : ArrayList<Icon2Name>?) : BaseQuickAdapter<Icon2Name, BaseViewHolder>(R.layout.item_avatar, datas) {
    override fun convert(helper: BaseViewHolder?, item: Icon2Name?) {
        helper!!.getView<ImageView>(R.id.iv_avatar)
                .let { it.loadRoundConner(it.context, item!!.avatarUrl) }
        helper.setText(R.id.tv_name, item!!.name)
    }
}