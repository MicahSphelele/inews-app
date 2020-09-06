package sphe.inews.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import kotlinx.android.synthetic.main.activity_splash.*
import sphe.inews.R
import sphe.inews.ui.main.MainActivity
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Named

class SplashActivity : BaseActivity() {

    @Inject
    @Named(Constants.NAMED_APP_VERSION)
    lateinit var appVersion: String

    @Inject
    @Named(Constants.NAMED_STORAGE)
    lateinit var appStorage: AppStorage

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)
        txt_app_version.text = appVersion
    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        Completable.timer(
            3, TimeUnit.SECONDS,
            AndroidSchedulers.mainThread()
        ).subscribe {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}