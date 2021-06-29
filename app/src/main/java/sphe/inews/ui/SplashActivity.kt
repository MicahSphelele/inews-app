package sphe.inews.ui

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatDelegate
import androidx.databinding.DataBindingUtil
import dagger.hilt.android.AndroidEntryPoint
import sphe.inews.R
import sphe.inews.databinding.ActivitySplashBinding
import sphe.inews.ui.main.MainActivity
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import javax.inject.Inject
import javax.inject.Named

@AndroidEntryPoint
class SplashActivity : BaseActivity() {

    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage

    @JvmField
    @Inject
    @Named(Constants.NAMED_IS_ON_TEST_MODE)
    var isOnTest: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
        val binding: ActivitySplashBinding =
            DataBindingUtil.setContentView(this, R.layout.activity_splash)

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

        binding.txtAppVersion.text = appVersion

    }

    override fun onStart() {
        super.onStart()
        Log.d("@TAG", "isOnTest : $isOnTest")
        Handler(Looper.getMainLooper()).postDelayed({
            startActivity(Intent(this, MainActivity::class.java))
            finish()
        },3000)
    }
}