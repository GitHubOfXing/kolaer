package com.uxin.baselib

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentStatePagerAdapter
import androidx.viewpager.widget.PagerAdapter

import java.util.ArrayList

/**
 * lichenxing
 */
class BasePagerAdapter(fm: FragmentManager) :
    FragmentStatePagerAdapter(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT) {

    private val fragmentList = ArrayList<BaseFragment>()

    fun init(list: List<BaseFragment>) {
        fragmentList.clear()
        fragmentList.addAll(list)
    }

    override fun getItemPosition(`object`: Any): Int {
        return PagerAdapter.POSITION_NONE
    }

    override fun getItem(position: Int): Fragment {
        return (if (fragmentList != null && position < fragmentList.size) {
            fragmentList[position]
        } else null)!!
    }

    override fun getCount(): Int {
        return fragmentList.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return if (getItem(position) is BaseFragment) {
            (getItem(position) as BaseFragment).title
        } else super.getPageTitle(position)
    }
}