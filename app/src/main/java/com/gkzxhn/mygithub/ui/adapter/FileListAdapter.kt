package com.gkzxhn.mygithub.ui.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Content

/**
 * Created by 方 on 2017/12/13.
 */
class FileListAdapter(var datas : List<Content>?) : BaseQuickAdapter<Content, BaseViewHolder>(R.layout.item_file_list, datas) {

    override fun convert(helper: BaseViewHolder?, item: Content?) {
        when (item!!.type) {
            "dir" -> {
                helper!!.setImageResource(R.id.iv_img, R.drawable.directory)
            }
            "file" -> {
                helper!!.setImageResource(R.id.iv_img, R.drawable.file)
            }
            else -> {
            }
        }
        helper!!.setText(R.id.tv_title, item.name)
    }
}