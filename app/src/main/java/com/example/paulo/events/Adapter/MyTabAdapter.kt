package com.example.paulo.events.Adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.app.ListFragment
import com.example.paulo.events.Tabs.TabA
import com.example.paulo.events.Tabs.TabB
import com.example.paulo.events.Tabs.TabC

/**
 * Created by Paulo on 18/07/2017.
 */
class MyTabAdapter(fm: FragmentManager?, var nTableTitles: ArrayList<String>) : FragmentPagerAdapter(fm) {

    val FragmentA : TabA = TabA()
    val FragmentB : TabB = TabB()


    override fun getItem(position: Int): Fragment {
        when(position) {
            0 -> return FragmentB
            else -> return FragmentA
        }
    }


    override fun getCount(): Int {
        return nTableTitles.size
    }



    override fun getPageTitle(position: Int): CharSequence {
        return this.nTableTitles[position]
    }


}