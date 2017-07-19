package com.example.paulo.events.Tabs

import android.app.Activity
import android.app.FragmentManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.paulo.events.MainActivity
import com.example.paulo.events.R


class TabB : android.support.v4.app.Fragment() {

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
        val view : View = activity.layoutInflater.inflate(R.layout.tab_b,container,false)

        return view
    }
}