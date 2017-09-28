package com.example.paulo.events

import android.app.Fragment
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.TabLayout
import android.support.v4.view.GravityCompat
import android.support.v4.view.ViewPager
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.example.paulo.events.Adapter.MyTabAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar_main.*
import android.content.Intent
import android.graphics.Color
import android.os.PersistableBundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.content.ContextCompat
import kotlinx.android.synthetic.main.screen.*
import java.lang.reflect.Array
import android.support.v4.view.ViewPager.OnPageChangeListener
import android.support.v4.widget.SwipeRefreshLayout
import android.view.View
import android.widget.Toolbar
import android.widget.TextView
import com.example.paulo.events.Tabs.TabB
import org.jetbrains.anko.act
import org.jetbrains.anko.contentView


class MainActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener {

    lateinit var turnOn : MyTabAdapter

    override fun onPostCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onPostCreate(savedInstanceState, persistentState)


    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //var a = false
        //setContentView(R.layout.activity_login)

        //if (a) {
            setContentView(R.layout.activity_main)
            setSupportActionBar(toolbar)
            val toolbarN = findViewById<android.support.v7.widget.Toolbar>(R.id.toolbar)
            toolbarN.setBackgroundColor(resources.getColor(R.color.aplicacao))

            if (android.os.Build.VERSION.SDK_INT > 21) {
                window.statusBarColor = resources.getColor(R.color.aplicacao)
            }

            /* fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }*/

            var mTabLayout = findViewById<com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView>(R.id.tab_layout2) as com.luseen.luseenbottomnavigation.BottomNavigation.BottomNavigationView
            var mPagerView = findViewById<ViewPager>(R.id.view_pager2) as ViewPager

            var nstring = ArrayList<String>()
            nstring.add("Próximos Eventos")
            nstring.add("Meus Eventos")


            var pg: MyTabAdapter = MyTabAdapter(getSupportFragmentManager(), nstring)
            mPagerView.adapter = pg

            turnOn = pg
            val cores : IntArray = intArrayOf(resources.getColor(R.color.aplicacao), resources.getColor(R.color.aplicacao))
            val img : IntArray = intArrayOf(R.drawable.event, R.drawable.favorite)

            mTabLayout.setUpWithViewPager(mPagerView,cores,img)




            val toggle = ActionBarDrawerToggle(
                    this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
            drawer_layout.addDrawerListener(toggle)
            toggle.syncState()

            //Menu NAv view
            nav_view.setNavigationItemSelectedListener(this)
            nav_view.setBackgroundColor(resources.getColor(R.color.fundo_tab))


            mPagerView.setOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrollStateChanged(state: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {}

            override fun onPageSelected(position: Int) {
                mTabLayout.selectTab(position)

                if (android.os.Build.VERSION.SDK_INT > 21) {

                    if (position == 0){
                        toolbarN.title = "Próximos Eventos"
                    }else toolbarN.title = "Meus Eventos"
                }
        }
        })
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu): Boolean {
        super.onPrepareOptionsMenu(menu)
        //val settingsItem = menu.findItem(R.id.action_settings)
        //settingsItem.setIcon(R.drawable.event)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        item.setIcon(R.drawable.refresh)

        var tab: TabB = turnOn.getItem(0) as TabB
        tab.takeEventsWeb()


        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }


            R.id.nav_share -> {

            }
            R.id.nav_send -> {
                val intent = Intent(this@MainActivity, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

}
