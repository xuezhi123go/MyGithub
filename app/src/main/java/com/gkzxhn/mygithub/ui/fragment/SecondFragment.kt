package com.gkzxhn.mygithub.ui.fragment

import android.content.Intent
import android.content.res.Resources
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.gkzxhn.balabala.base.BaseFragment
import com.gkzxhn.balabala.mvp.contract.BaseView
import com.gkzxhn.mygithub.R
import kotlinx.android.synthetic.main.fragment_second.*

/**
 * Created by Xuezhi on 2017/12/12.
 */
class SecondFragment : BaseFragment(), BaseView {

    private lateinit var mFragments: ArrayList<Fragment>

    private lateinit var theme: Resources.Theme

    override fun launchActivity(intent: Intent) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun showMessage() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun killMyself() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun initContentView() {
        initFragments()
        setOnClickListener()
    }

    override fun initView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        return inflater!!.inflate(R.layout.fragment_second, container, false)

    }

    private fun initFragments() {
        mFragments = arrayListOf()
        val eventFragment = EventFragment()
        val activityFragment = ActivityFragment()
        //val eventFragment2 = EventFragment()
        mFragments.add(eventFragment)
        mFragments.add(activityFragment)

        vp_second.adapter = object : FragmentPagerAdapter(childFragmentManager) {
            override fun getItem(position: Int): Fragment = mFragments[position]
            override fun getCount(): Int = 2
        }
        vp_second.offscreenPageLimit = 1
        vp_second.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {
            }

            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
            }

            override fun onPageSelected(position: Int) {

                when (position) {
                    0 -> {
                        LeftButtonShow()
                    }
                    1 -> {
                        RightButtonShow()
                    }
                }
            }
        })
    }

    override fun getStatusBar(): View? {
        return status_view_notifications
    }

    override fun getToolbar(): Toolbar? {
        return toolbar_notifications
    }

    fun LeftButtonShow() {
        tv_left_button.setBackgroundResource(R.drawable.notification_left_selected)
        tv_left_button.setTextColor(android.graphics.Color.BLACK)
        tv_right_button.setBackgroundResource(R.drawable.notification_right_unselect)
        tv_right_button.setTextColor(android.graphics.Color.WHITE)
    }

    fun RightButtonShow() {
        tv_left_button.setBackgroundResource(R.drawable.notification_left_unselect)
        tv_left_button.setTextColor(android.graphics.Color.WHITE)
        tv_right_button.setBackgroundResource(R.drawable.notification_right_selected)
        tv_right_button.setTextColor(android.graphics.Color.BLACK)
    }

    fun setOnClickListener() {
        tv_left_button.setOnClickListener {
            vp_second.arrowScroll(View.FOCUS_LEFT)
        }
        tv_right_button.setOnClickListener {
            vp_second.arrowScroll(View.FOCUS_RIGHT)
        }
    }
}

