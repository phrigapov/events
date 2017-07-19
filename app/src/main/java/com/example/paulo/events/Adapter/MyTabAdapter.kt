package com.example.paulo.events.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.example.paulo.events.Tabs.TabA
import com.example.paulo.events.Tabs.TabB
import com.example.paulo.events.Tabs.TabC

/**
 * Created by Paulo on 18/07/2017.
 */
class MyTabAdapter(fm: FragmentManager?, var nTableTitles: ArrayList<String>) : FragmentPagerAdapter(fm) {


    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return TabA()
            1 -> return TabB()
            else -> return TabC()
        }
    }


    override fun getCount(): Int {
        return 3
    }



    override fun getPageTitle(position: Int): CharSequence {
        return this.nTableTitles[position]
    }


}