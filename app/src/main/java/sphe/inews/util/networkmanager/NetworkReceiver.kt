package sphe.inews.util.networkmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log

class NetworkReceiver(private var networkReceiverInterface: NetworkReceiverInterface) : BroadcastReceiver() {

    companion object{
        private const val TAG = "@KTX_NETWORK"
    }

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == "android.net.conn.CONNECTIVITY_CHANGE"){

            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo //DEPRECATION


            if(netInfo == null){ //If it is in airplane mode
                Log.i(TAG,"Device is on airplane mode...")
                networkReceiverInterface.onConnectedToNetwork(NetworkType.AIRPLANE_MODE)
                return
            }

            val networkType = if(netInfo.type == ConnectivityManager.TYPE_MOBILE){
                NetworkType.MOBILE_DATA
            }else{
                NetworkType.WIFI
            }

            if(netInfo.isConnected){
                Log.i(TAG,"Device is connected to data or wifi..")
                networkReceiverInterface.onConnectedToNetwork(networkType)
                return
            }
            Log.i(TAG,"Device is not connected to data or wifi..")
            networkReceiverInterface.onConnectedToNetwork(networkType)

        }
    }
}