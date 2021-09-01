package sphe.inews.ui

import android.content.IntentFilter
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.google.android.gms.location.LocationRequest
import com.google.android.material.snackbar.Snackbar
import sphe.inews.R
import sphe.inews.models.NetworkData
import sphe.inews.util.networkmanager.NetworkReceiver

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var networkReceiver: NetworkReceiver
    private lateinit var locationRequest: LocationRequest

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        networkReceiver = NetworkReceiver()
        //locationRequest =
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

    fun showNetworkStateBar(view: View): Snackbar {
        val customSnackBar =
            Snackbar.make(view, "", Snackbar.LENGTH_INDEFINITE)
        val layout = customSnackBar.view as Snackbar.SnackbarLayout
        val customSnackView: View = layoutInflater.inflate(R.layout.network_error_view, null)
        // We can also customize the above controls
        layout.setPadding(0, 0, 0, 0)
        layout.addView(customSnackView, 0)
        return customSnackBar
    }

    fun showToastMessage(text: String) {
        val toast = Toast.makeText(this, text, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, toast.xOffset / 2, toast.yOffset / 2)
        toast.show()
    }

}