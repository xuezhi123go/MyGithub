package com.gkzxhn.mygithub.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.mygithub.R

/**
 * Created by 方 on 2017/10/19.
 */
class HomeFragment : BaseFragment(){
    override fun initContentView() {
//        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }
}