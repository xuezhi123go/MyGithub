package com.gkzxhn.mygithub.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Starred

/**
 * Created by Xuezhi on 2017/10/26.
 */
class StarsAdapter(datas: List<Starred>?) : BaseQuickAdapter<Starred, BaseViewHolder>(R.layout.item_stars, datas) {
    override fun convert(helper: BaseViewHolder?, item: Starred?) {
        helper!!.setText(R.id.tv_stars, item!!.full_name)
        helper.setText(R.id.tv_stars_body, item.description)
    }
}