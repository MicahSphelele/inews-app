package sphe.inews.ui.main

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import kotlinx.android.synthetic.main.activity_main.*
import sphe.inews.R
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.dialogfragments.AboutDialogFragment
import javax.inject.Inject

class MainActivity : BaseActivity() {


    @Inject
    lateinit var aboutFragmentDialog: AboutDialogFragment

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
                Toast.makeText(this,"Feature coming soon. Will show COVID-19 stats based on country",Toast.LENGTH_SHORT).show()
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

}
