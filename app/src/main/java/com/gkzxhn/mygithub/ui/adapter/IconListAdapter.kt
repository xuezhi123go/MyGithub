package com.gkzxhn.mygithub.ui.adapter

import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Organization
import com.gkzxhn.mygithub.extension.load

/**
 * Created by 方 on 2017/10/27.
 */
class IconListAdapter(datas : List<Organization>?) : BaseQuickAdapter<Organization, BaseViewHolder>(R.layout.item_user, datas) {
    override fun convert(helper: BaseViewHolder?, item: Organization?) {
        helper!!.getView<ImageView>(R.id.iv_icon)
                .let { it.load(it.context, item!!.avatar_url) }
    }
}