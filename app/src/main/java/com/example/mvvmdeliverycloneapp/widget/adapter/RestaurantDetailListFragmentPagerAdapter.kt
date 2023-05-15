package com.example.mvvmdeliverycloneapp.widget.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.mvvmdeliverycloneapp.screen.main.home.restaurant.detail.menu.RestaurantMenuListFragment

class RestaurantDetailListFragmentPagerAdapter : FragmentStateAdapter {

    private val fragmentList: List<Fragment>

    constructor(
        activity: FragmentActivity,
        fragmentList: List<Fragment>
    ): super(activity) {
        this.fragmentList = fragmentList
    }

    override fun getItemCount(): Int = fragmentList.size

    override fun createFragment(position: Int): Fragment = fragmentList[position]
}