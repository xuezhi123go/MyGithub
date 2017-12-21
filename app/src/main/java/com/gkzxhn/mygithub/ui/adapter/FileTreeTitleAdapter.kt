package com.gkzxhn.mygithub.ui.adapter

import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.base.App

/**
 * Created by 方 on 2017/12/13.
 */
class FileTreeTitleAdapter(var datas : ArrayList<String>?) : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_file_title, datas) {

    override fun convert(helper: BaseViewHolder?, item: String?) {
        helper!!.setText(R.id.tv_file_title, item)
        val fileTitle = helper.getView<TextView>(R.id.tv_file_title)
        if (helper.layoutPosition == datas!!.size - 1) {
            helper.setTextColor(R.id.tv_file_title, App.getInstance().resources.getColor(R.color.text_black))
            fileTitle.typeface = Typeface.defaultFromStyle(Typeface.BOLD);//加粗
        }else {
            helper.setTextColor(R.id.tv_file_title, App.getInstance().resources.getColor(R.color.gray_line))
            fileTitle.typeface = Typeface.defaultFromStyle(Typeface.NORMAL);//不加粗
        }
    }
}