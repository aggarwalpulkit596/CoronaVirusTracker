package com.puldroid.coronavirustracker

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.puldroid.coronavirustracker.adapter.ViewPagerAdapter
import com.puldroid.coronavirustracker.network.Client
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var mViewPagerAdapter: ViewPagerAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mViewPagerAdapter = ViewPagerAdapter(supportFragmentManager)
        viewPager.adapter = mViewPagerAdapter
        viewPager.offscreenPageLimit = 3
        title = "Corona Virus Tracker"

        bottomNav.setOnNavigationItemSelectedListener(this)

        GlobalScope.launch {
            val response = withContext(Dispatchers.IO) { Client.api.getCases() }
            Log.i("Networking", response.body().toString())
            response.body()
        }
    }

    override fun onNavigationItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            R.id.navigation_stats -> viewPager.currentItem = 0
            R.id.navigation_maps -> viewPager.currentItem = 1
            R.id.navigation_advice -> viewPager.currentItem = 2
            R.id.navigation_about -> viewPager.currentItem = 3

        }
        return true
    }
}
