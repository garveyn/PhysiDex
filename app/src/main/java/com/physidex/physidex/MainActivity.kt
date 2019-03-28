package com.physidex.physidex

import android.content.Context
import android.os.Bundle
// import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.content.Intent
import android.support.design.widget.NavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.view.GravityCompat.*
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBar
import android.support.v7.widget.Toolbar
import android.view.LayoutInflater
import android.widget.EditText

import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.drawer_test.*

const val DISPLAY_CARD = "com.physidex.physidex.CARD"

open class MainActivity : AppCompatActivity() {

    private lateinit var mDrawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_test)

        mDrawerLayout = findViewById(R.id.drawer_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val fragmentManager = supportFragmentManager


        val navigationView: NavigationView = findViewById(R.id.nav_view)

        navigationView.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked=true

            mDrawerLayout.closeDrawers()

            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment: Fragment

            when (menuItem.itemId) {
                R.id.action_settings -> newFragment = SettingsPage()
                R.id.my_binder_menu -> newFragment = MyBinderPage()
                R.id.game_manager_menu -> newFragment = GameManagerPage()
                R.id.deck_manager_menu -> newFragment = DeckManagerPage()
                R.id.search_page -> newFragment = SearchFragment()
                else -> newFragment = Fragment()
            }

            fragmentTransaction.replace(R.id.fragment_frame, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            true
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                mDrawerLayout.openDrawer(START)
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }


    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    external fun stringFromJNI(): String

    companion object {

        // Used to load the 'native-lib' library on application startup.
        init {
            System.loadLibrary("native-lib")
        }
    }
}
