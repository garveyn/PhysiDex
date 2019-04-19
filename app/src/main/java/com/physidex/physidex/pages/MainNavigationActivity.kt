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
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.physidex.physidex.R
import kotlinx.android.synthetic.main.drawer_test.*
import androidx.navigation.ui.NavigationUI.*
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

const val DISPLAY_CARD = "com.physidex.physidex.CARD"
const val MY_BINDER_CARDS = "com.physidex.physidex.BINDER_CARDS"
const val DISPLAY_DECK = "com.physidex.physidex.DECK"

open class MainActivity : AppCompatActivity() {

    val topLevelDestinations = setOf(R.id.homeFragment, R.id.gameManagerHomeFragment,
            R.id.searchHomeFragment, R.id.deckManagerFragment, R.id.myBinderHomeFragment,
            R.id.settingsFragment)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.drawer_test)
        setupNavigation()
    }


    override fun onSupportNavigateUp(): Boolean {
        return navigateUp(findNavController(this, R.id.fragment),
                AppBarConfiguration(topLevelDestinations, drawer_layout))
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    fun setupNavigation() {

        // inspired by:
        // https://stackoverflow.com/questions/51528870/android-navigation-architecture-component-nav-drawer-icons
        val navController = findNavController(this, R.id.fragment)
        val appBarConfiguration = AppBarConfiguration(topLevelDestinations, drawer_layout)

        nav_view.setNavigationItemSelectedListener { menuItem ->

            menuItem.isChecked=true

            drawer_layout.closeDrawers()

            true
        }

        setSupportActionBar(toolbar)
        setupActionBarWithNavController(navController, appBarConfiguration)

        nav_view.setupWithNavController(navController)

    }

    // function from http://www.albertgao.xyz/2018/04/13/how-to-add-additional-parameters-to-viewmodel-via-kotlin/
    inline fun <VM : ViewModel> viewModelFactory(crossinline f: () -> VM) =
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(aClass: Class<T>):T = f() as T
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
