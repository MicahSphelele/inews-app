package sphe.inews.presentation.ui.activities

import android.app.Dialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.ActivityMainBinding
import sphe.inews.domain.enums.AppTheme
import sphe.inews.presentation.ui.dialogfragments.AboutDialogFragment
import sphe.inews.presentation.ui.dialogfragments.covid.CovidStatDialogFragment
import sphe.inews.presentation.ui.permission.PermissionsFragment
import sphe.inews.util.Constants
import sphe.inews.util.LocationUtils
import sphe.inews.data.local.storage.AppStorage
import sphe.inews.domain.models.storage.Storage
import javax.inject.Inject
import javax.inject.Named


@AndroidEntryPoint
class MainActivity : BaseActivity(), NavController.OnDestinationChangedListener {

    private lateinit var aboutFragmentDialog: AboutDialogFragment

    private lateinit var covidStatDialogFragment: CovidStatDialogFragment

    private lateinit var binding: ActivityMainBinding

    private lateinit var locationRequestBuilder: LocationSettingsRequest

    private lateinit var settingsLauncher: ActivityResultLauncher<IntentSenderRequest>

    private var isNetworkConnected = false

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: Storage

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
        val snackBar = showNetworkStateBar(binding.mainParentLayout)

        networkState.observe(this, {

            isNetworkConnected = it.isConnected

            if (!isNetworkConnected) {
                binding.appBottomNavigation.visibility = View.INVISIBLE
                snackBar.show()
                return@observe
            }

            if (snackBar.isShown) {
                snackBar.dismiss()
                binding.appBottomNavigation.visibility = View.VISIBLE
            }
        })

        settingsLauncher =
            registerForActivityResult(ActivityResultContracts.StartIntentSenderForResult()) {
                if (it.resultCode == RESULT_OK) {
                    if (navController.currentDestination?.label != "Weather") {
                        navController.navigate(R.id.weatherFragment, null, null, null)
                    }
                } else {
                    showToastMessage(getString(R.string.msg_gps_access_denied))
                }
            }

        locationRequestBuilder = LocationSettingsRequest.Builder()
            .addLocationRequest(LocationUtils.getLocationRequest())
            .setAlwaysShow(true)
            .build()

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
                if (navController.currentDestination?.label != "Bookmark") {
                    navController.navigate(R.id.bookmarkFragment, null, null, null)
                }
            }
            R.id.action_about -> {
                if (!isNetworkConnected) {
                    showToastMessage(getString(R.string.msg_connect_to_wifi))
                    return false
                }
                aboutFragmentDialog = AboutDialogFragment()
                aboutFragmentDialog.show(supportFragmentManager, "aboutFragmentDialog")
            }
            R.id.action_weather -> {
                if (!isNetworkConnected) {
                    showToastMessage(getString(R.string.msg_connect_to_wifi))
                    return false
                }
                handleWeatherScreen()
            }
            R.id.action_corona -> {
                if (!isNetworkConnected) {
                    showToastMessage(getString(R.string.msg_connect_to_wifi))
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
        val index = appStorage.getIntData(Constants.KEY_THEME)
        var selectedIndex: Int = index

        val builder = MaterialAlertDialogBuilder(this, R.style.MaterialThemeDialog)
            .setTitle(getString(R.string.title_select_theme))
            .setIcon(R.drawable.logo)
            .setSingleChoiceItems(R.array.theme_modes, index) { _, which ->
                selectedIndex = which
            }
            .setPositiveButton(getString(R.string.txt_save)) { dialog, _ ->
                appStorage.saveIntData(Constants.KEY_THEME, selectedIndex)
                setTheme()
                dialog.dismiss()
            }
            .setNegativeButton(getString(R.string.txt_cancel)) { dialog, _ ->
                dialog.dismiss()
            }
        dialog = builder.create()
        dialog?.show()
    }

    private fun handleWeatherScreen() {
        if (LocationUtils.isLocationPermissionEnabled(this)) {

            LocationServices.getSettingsClient(this)
                .checkLocationSettings(locationRequestBuilder)
                .addOnCompleteListener {

                    if (it.isSuccessful) {
                        if (navController.currentDestination?.label != "Weather") {
                            navController.navigate(R.id.weatherFragment, null, null, null)
                        }
                    }

                }.addOnFailureListener {
                    it as ResolvableApiException

                    if (it.statusCode == LocationSettingsStatusCodes.RESOLUTION_REQUIRED) {
                        settingsLauncher.launch(IntentSenderRequest.Builder(it.resolution).build())
                    }
                }
            return
        }
        if (navController.currentDestination?.label != "AppPermissions") {
            val bundle = Bundle().apply {
                putString(
                    PermissionsFragment.EXTRA_PERMISSION_TYPE,
                    PermissionsFragment.TYPE_LOCATION
                )
            }
            navController.navigate(R.id.permissionsFragment, bundle, null, null)
        }
    }

    private fun setTheme() {
        when (AppTheme.values()[appStorage.getIntData(Constants.KEY_THEME)]) {
            AppTheme.LIGHT_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
            AppTheme.DARK_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            }
            AppTheme.SYSTEM_MODE -> {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
            }
        }
    }
}
