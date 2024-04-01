package sphe.inews.presentation.ui.base

import android.annotation.SuppressLint
import android.content.IntentFilter
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.google.android.material.snackbar.Snackbar
import sphe.inews.R
import sphe.inews.domain.models.NetworkData
import sphe.inews.presentation.recievers.NetworkReceiver

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var networkReceiver: NetworkReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkReceiver = NetworkReceiver()
    }

    override fun onStart() {
        super.onStart()
        val intentFilter = IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")
        registerReceiver(networkReceiver, intentFilter)
    }

    override fun onStop() {
        super.onStop()
        unregisterReceiver(networkReceiver)
    }

    val networkState: LiveData<NetworkData> get() = networkReceiver.networkState

    @SuppressLint("RestrictedApi")
    fun showNetworkStateBar(view: View): Snackbar {
        val snackBarView = Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
        val layout = snackBarView.view as Snackbar.SnackbarLayout
        val viewGroup = findViewById<ViewGroup>(android.R.id.content)
        val customSnackView: View = layoutInflater.inflate(R.layout.network_error_view, viewGroup, false)
        // We can also customize the above controls
        layout.setPadding(0, 0, 0, 0)
        layout.addView(customSnackView, 0)
        return snackBarView
    }

    fun showToastMessage(text: String) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, toast.xOffset / 2, toast.yOffset / 2)
        toast.show()
    }

}