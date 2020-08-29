package sphe.inews.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import sphe.inews.R
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.dialogfragments.AboutDialogFragment
import sphe.inews.ui.main.dialogfragments.covid.CovidStatDialogFragment
import javax.inject.Inject

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {


    @Suppress("unused")
    @Inject
    lateinit var aboutFragmentDialog: AboutDialogFragment

    @Suppress("unused")
    @Inject
    lateinit var covidStatDialogFragment: CovidStatDialogFragment

    @Suppress("unused")
    private lateinit var navController : NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(toolbar as Toolbar)

        navController = Navigation.findNavController(this,R.id.navigation_host_fragment)

        //Setup with bottom navigation
        NavigationUI.setupWithNavController(bottom_navigation,navController)

        //Setup with action bar
        //NavigationUI.setupActionBarWithNavController(this,navController)

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController,null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_about -> {
                aboutFragmentDialog.show(supportFragmentManager,"aboutFragmentDialog")
            }
            R.id.action_corona -> {
                covidStatDialogFragment.show(supportFragmentManager,"covidStatDialogFragment")
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        when(NavHostFragment.findNavController(navigation_host_fragment).currentDestination?.id){
            R.id.businessFragment -> finish()
        }

    }

    override fun onResume() {
        super.onResume()
        navController.addOnDestinationChangedListener(this)
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(this)
    }

    override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
        when(destination.label){
            "Business", "Health",
            "Sport", "Technology","Entertainment" -> {
                bottom_navigation.visibility = View.VISIBLE
            }
            else -> {
                bottom_navigation.visibility = View.GONE
            }

        }
    }

}
