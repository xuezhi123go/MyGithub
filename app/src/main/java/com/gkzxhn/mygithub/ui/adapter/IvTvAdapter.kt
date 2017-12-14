package com.gkzxhn.mygithub.ui.adapter

import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.entity.IvTvItemBean
import com.gkzxhn.mygithub.extension.dp2px

/**
 * Created by 方 on 2017/12/13.
 */
class IvTvAdapter(datas : List<IvTvItemBean>?) : BaseQuickAdapter<IvTvItemBean, BaseViewHolder>(R.layout.item_iv_tv, datas) {

    override fun convert(helper: BaseViewHolder?, item: IvTvItemBean?) {
        val baseView = helper!!.getView<RelativeLayout>(R.id.rl_base)
        val layoutParams = baseView.layoutParams as RecyclerView.LayoutParams
        if (helper.layoutPosition == 0 || helper.layoutPosition == 4) {
            layoutParams.topMargin = 20f.dp2px().toInt()
            baseView.layoutParams = layoutParams
            helper.setVisible(R.id.top_line, true)
        }else {
            helper.setVisible(R.id.top_line, false)
        }
        val bottom_line = helper.getView<View>(R.id.bottom_line)
        val lineLayoutParams = bottom_line.layoutParams as RelativeLayout.LayoutParams
        if (helper.layoutPosition == 3 || helper.layoutPosition == 7) {
            lineLayoutParams.leftMargin = 0
            bottom_line.layoutParams = lineLayoutParams
        }
        baseView.isClickable = item!!.clickable
        helper.setImageResource(R.id.iv_left, item.ivResource)
        helper.setText(R.id.tv_title, item.tvTitle)
        if(item.tvRight is String) {
            helper.setText(R.id.tv_right, item.tvRight.let { it })
            helper.setBackgroundRes(R.id.tv_right, R.color.white)
        }else if(item.tvRight is Int) {
            helper.setBackgroundRes(R.id.tv_right, item.tvRight.let { it })
        }
        helper.setVisible(R.id.tv_right, item.clickable)
    }
}