package com.gkzxhn.mygithub.ui.fragment

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.mygithub.R
import kotlinx.android.synthetic.main.fragment_profile.*

/**
 * Created by 方 on 2017/10/23.
 */
class ProfileFragment : BaseFragment() {
    override fun initContentView() {
        rv_profile.layoutManager = LinearLayoutManager(context)
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        return inflater!!.inflate(R.layout.fragment_profile, container, false)
    }

    override fun getStatusBar(): View? {
        return status_view
    }

    override fun getToolbar(): Toolbar? {
        return toolbar
    }

    override fun setupComponent() {
        super.setupComponent()
    }
}