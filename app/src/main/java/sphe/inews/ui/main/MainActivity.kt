package sphe.inews.ui.main

import android.app.Dialog
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*
import sphe.inews.R
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.dialogfragments.AboutDialogFragment
import sphe.inews.ui.main.dialogfragments.covid.CovidStatDialogFragment
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import javax.inject.Inject
import javax.inject.Named

class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    companion object{
        const val TAG = "@KTX"
    }

    @Suppress("unused")
    @Inject
    lateinit var aboutFragmentDialog: AboutDialogFragment

    @Suppress("unused")
    @Inject
    lateinit var covidStatDialogFragment: CovidStatDialogFragment

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage

    @Suppress("unused")
    private lateinit var navController : NavController

    private var dialog : Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setTheme()
        Log.d(TAG,"MainActivity")

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
            R.id.action_theme -> {
               showThemeDialog()
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
        if(dialog!=null){
            dialog?.show()
        }
    }

    override fun onPause() {
        super.onPause()
        navController.removeOnDestinationChangedListener(this)
        if(dialog!=null){
            if(dialog!!.isShowing){
                dialog?.dismiss()
            }
        }
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

    private fun showThemeDialog(){

        val list = resources.getStringArray(R.array.theme_modes)
        val index = Constants.selectThemeIndex(appStorage.getStringData(Constants.KEY_THEME,Constants.DEFAULT_THEME))
        var selectedIndex :Int = index

        val builder =MaterialAlertDialogBuilder(this,R.style.MaterialThemeDialog)
        .setTitle("Theme")
        .setIcon(R.drawable.logo)
        .setSingleChoiceItems(R.array.theme_modes,index) { _, which ->
            selectedIndex = which
        }
        .setPositiveButton("SAVE"){dialog, _ ->
            appStorage.saveStringData(Constants.KEY_THEME,Constants.selectThemeValue(list[selectedIndex]))
            setTheme()
            dialog.dismiss()
        }
        .setNegativeButton("CANCEL"){dialog, _ ->
            dialog.dismiss()
        }
        dialog = builder.create()
        dialog?.show()
    }

    private fun setTheme(){
        when(appStorage.getStringData(Constants.KEY_THEME,Constants.DEFAULT_THEME)){
            "light" ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "dark" ->{
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }else ->{
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
        }
    }

}
