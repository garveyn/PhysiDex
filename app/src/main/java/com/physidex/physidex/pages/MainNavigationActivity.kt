package com.physidex.physidex.pages

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import com.physidex.physidex.R
import kotlinx.android.synthetic.main.drawer_test.*
import androidx.navigation.ui.NavigationUI.*
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController

/**
 * The Main and only activity. This handles the navigation drawer, and sets up the ViewModel.
 * All fragments are loaded on top of this activity
 */
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
        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            if (destination.id == R.id.deckDetailActivity) {
                setupActionBarWithNavController(this, controller)
                fragment.setHasOptionsMenu(true)
            } else {
                val appBarConfiguration = AppBarConfiguration(topLevelDestinations, drawer_layout)

                nav_view.setNavigationItemSelectedListener { menuItem ->

                    menuItem.isChecked=true

                    drawer_layout.closeDrawers()

                    true
                }

                setSupportActionBar(toolbar)
                setupActionBarWithNavController(controller, appBarConfiguration)

                nav_view.setupWithNavController(controller)
            }

        }



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
