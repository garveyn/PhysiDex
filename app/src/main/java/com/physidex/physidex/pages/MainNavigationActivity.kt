package com.physidex.physidex.pages

import android.os.Bundle
// import android.support.design.widget.Snackbar
import androidx.appcompat.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.navigation.NavigationView
import androidx.fragment.app.Fragment
import androidx.core.view.GravityCompat.*
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.ActionBar
import androidx.appcompat.widget.Toolbar
import com.physidex.physidex.R
import kotlinx.android.synthetic.main.drawer_test.*

const val DISPLAY_CARD = "com.physidex.physidex.CARD"
const val MY_BINDER_CARDS = "com.physidex.physidex.BINDER_CARDS"
const val DISPLAY_DECK = "com.physidex.physidex.DECK"

open class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout: DrawerLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_test)

        drawerLayout = findViewById(R.id.drawer_layout)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)
        val actionbar: ActionBar? = supportActionBar
        actionbar?.apply {
            setDisplayHomeAsUpEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_menu)
        }

        val fragmentManager = supportFragmentManager


        val navigationView: NavigationView = nav_view

        navigationView.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked=true

            drawerLayout.closeDrawers()

            val fragmentTransaction = fragmentManager.beginTransaction()
            val newFragment: Fragment

            when (menuItem.itemId) {
                R.id.action_settings -> {
                    newFragment = SettingsFragment()
                    toolbar.title = getString(R.string.action_settings)
                }
                R.id.my_binder_menu -> {
                    newFragment = MyBinderHomeFragment()
                    toolbar.title = getString(R.string.my_binder)
                }
                R.id.game_manager_menu -> {
                    newFragment = GameManagerHomeFragment()
                    toolbar.title = getString(R.string.game_manager)
                }
                R.id.deck_manager_menu -> {
                    newFragment = DeckManagerFragment()
                    toolbar.title = getString(R.string.deck_manager)
                }
                R.id.search_page -> {
                    newFragment = SearchHomeFragment()
                    toolbar.title = getString(R.string.search)
                }
                else -> newFragment = Fragment()
            }

            fragmentTransaction.replace(R.id.fragment, newFragment)
            fragmentTransaction.addToBackStack(null)
            fragmentTransaction.commit()

            true
        }
    }



    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                drawerLayout.openDrawer(START)
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
