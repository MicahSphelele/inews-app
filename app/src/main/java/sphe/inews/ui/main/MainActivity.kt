package sphe.inews.ui.main

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.ActivityMainBinding
import sphe.inews.ui.BaseActivity
import sphe.inews.ui.main.dialogfragments.AboutDialogFragment
import sphe.inews.ui.main.dialogfragments.covid.CovidStatDialogFragment
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    lateinit var aboutFragmentDialog: AboutDialogFragment

    lateinit var covidStatDialogFragment: CovidStatDialogFragment

    lateinit var binding: ActivityMainBinding

    private var _networkState = false

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage

    @Suppress("unused")
    private lateinit var navController: NavController

    private var dialog: Dialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        setTheme()

        setSupportActionBar(binding.appToolbar as Toolbar)

        navController = Navigation.findNavController(this, R.id.navigationHostFragment)

        //Setup with bottom navigation
        NavigationUI.setupWithNavController(binding.appBottomNavigation, navController)

        //Setup with action bar
        //NavigationUI.setupActionBarWithNavController(this,navController)
        val snackBar = showNetworkStateBar()

        networkState.observe(this, {

            _networkState = it.isConnected

            if (!_networkState) {
                snackBar.show()
                return@observe
            }

            if (snackBar.isShown){
                snackBar.dismiss()
            }
        })

    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, null)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.app_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {

            R.id.action_bookmarks -> {
                Toast.makeText(this, "Bookmark clicked", Toast.LENGTH_LONG).show()
            }
            R.id.action_about -> {
                if (!_networkState){
                    showToastMessage("Connect to Wifi/internet")
                    return false
                }
                aboutFragmentDialog = AboutDialogFragment()
                aboutFragmentDialog.show(supportFragmentManager, "aboutFragmentDialog")
            }
            R.id.action_corona -> {
                if (!_networkState){
                    showToastMessage("Connect to Wifi/internet")
                    return false
                }
                covidStatDialogFragment = CovidStatDialogFragment()
                covidStatDialogFragment.show(supportFragmentManager, "covidStatDialogFragment")
            }
            R.id.action_theme -> {
                showThemeDialog()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        super.onBackPressed()

        val fragment =
            supportFragmentManager.findFragmentById(R.id.navigationHostFragment) as Fragment

        when (NavHostFragment.findNavController(fragment).currentDestination?.id) {
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
        if (dialog != null) {
            if (dialog!!.isShowing) {
                dialog?.dismiss()
            }
        }
    }

    override fun onDestinationChanged(
        controller: NavController,
        destination: NavDestination,
        arguments: Bundle?
    ) {
        when (destination.label) {
            "Business", "Health",
            "Sport", "Technology", "Entertainment" -> {
                binding.appBottomNavigation.visibility = View.VISIBLE
            }
            else -> {
                binding.appBottomNavigation.visibility = View.GONE
            }

        }
    }

    private fun showThemeDialog() {

        val list = resources.getStringArray(R.array.theme_modes)
        val index = Constants.selectThemeIndex(
            appStorage.getStringData(
                Constants.KEY_THEME,
                Constants.DEFAULT_THEME
            )
        )
        var selectedIndex: Int = index

        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialThemeDialog)
            .setTitle("Select Theme")
            .setIcon(R.drawable.logo)
            .setSingleChoiceItems(R.array.theme_modes, index) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton("SAVE") { dialog, _ ->
                appStorage.saveStringData(
                    Constants.KEY_THEME,
                    Constants.selectThemeValue(list[selectedIndex])
                )
                setTheme()
                dialog.dismiss()
            }
            .setNegativeButton("CANCEL") { dialog, _ ->
                dialog.dismiss()
            }
        dialog = builder.create()
        dialog?.show()
    }

    private fun setTheme() {
        when (appStorage.getStringData(Constants.KEY_THEME, Constants.DEFAULT_THEME)) {
            "light" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            "dark" -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            else -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }

}
