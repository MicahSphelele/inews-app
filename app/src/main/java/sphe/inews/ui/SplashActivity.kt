package sphe.inews.ui

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.WindowManager
import io.reactivex.Completable
import io.reactivex.android.schedulers.AndroidSchedulers
import sphe.inews.R
import sphe.inews.ui.main.MainActivity
import java.util.concurrent.TimeUnit

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN)
        setContentView(R.layout.activity_splash)

    }

    @SuppressLint("CheckResult")
    override fun onStart() {
        super.onStart()
        Completable.timer(
            2, TimeUnit.SECONDS,
            AndroidSchedulers.mainThread()
        ).subscribe {
            startActivity(Intent(this,MainActivity::class.java))
            finish()
        }
    }
}