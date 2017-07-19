package com.example.paulo.events.Tabs

import android.graphics.Color.parseColor
import android.R.id.tabs
import android.support.v4.view.ViewPager
import android.os.Bundle
import android.view.ViewGroup
import android.view.LayoutInflater
import com.example.paulo.events.MainActivity
import android.app.Activity
import android.app.FragmentManager
import android.graphics.Color
import android.view.View
import com.example.paulo.events.R


class TabC : android.support.v4.app.Fragment() {

    internal var fragmentManager: FragmentManager? = null
    private var myContext: MainActivity? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        myContext = activity as MainActivity?
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view : View = activity.layoutInflater.inflate(R.layout.tab_c,container,false)

        return view
    }
}