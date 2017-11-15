package com.gkzxhn.mygithub.ui.adapter

import android.os.Parcelable
import android.text.TextUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.BaseViewHolder
import com.gkzxhn.mygithub.R
import com.gkzxhn.mygithub.bean.info.Repo
import com.gkzxhn.mygithub.bean.info.TrendingItem

/**
 * Created by 方 on 2017/10/24.
 */
class RepoListAdapter(datas: List<Parcelable>?) : BaseQuickAdapter<Parcelable, BaseViewHolder>(R.layout.item_repo, datas) {


    override fun convert(helper: BaseViewHolder?, item: Parcelable?) {
        if (item is Repo) {
            helper!!.setText(R.id.tv_name, item.full_name)
            helper.setText(R.id.tv_desc, item.description)
            helper.setText(R.id.tv_language, item.language.let {
                if (TextUtils.isEmpty(it)) {
                    return@let "unknown"
                } else {
                    return@let it
                }
            })
            helper.setText(R.id.tv_star, item.stargazers_count.toString())
            helper.setText(R.id.tv_fork, item.forks_count.toString())
        }else if (item is TrendingItem) {
            helper!!.setText(R.id.tv_name, item.repo)
            helper.setText(R.id.tv_desc, item.desc)
            helper.setText(R.id.tv_language, item.lang.let {
                if (TextUtils.isEmpty(it)) {
                    return@let "unknown"
                } else {
                    return@let it
                }
            })
            helper.setText(R.id.tv_star, item.stars)
            helper.setText(R.id.tv_fork, item.forks)
        }
    }

}