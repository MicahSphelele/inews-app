package sphe.inews.util.networkmanager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sphe.inews.enums.NetworkType
import sphe.inews.models.NetworkData
import sphe.inews.util.Constants

class NetworkReceiver : BroadcastReceiver() {

     private var networkReporter: MutableLiveData<NetworkData> =
         MutableLiveData<NetworkData>().apply { NetworkData(NetworkType.AIRPLANE_MODE,false)}

    @Suppress("DEPRECATION")
    override fun onReceive(context: Context?, intent: Intent?) {

        if(intent?.action == "android.net.conn.CONNECTIVITY_CHANGE"){

            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val netInfo = connectivityManager.activeNetworkInfo //DEPRECATION

            if(netInfo == null){ //If it is in airplane mode
                Log.i(Constants.DEBUG_TAG,"Device is on airplane mode...")
                networkReporter.postValue(NetworkData(NetworkType.AIRPLANE_MODE,false))
                return
            }

            val networkType = if(netInfo.type == ConnectivityManager.TYPE_MOBILE){
                NetworkType.MOBILE_DATA
            }else{
                NetworkType.WIFI
            }

            if(netInfo.isConnected){
                Log.i(Constants.DEBUG_TAG,"Device is connected to data or wifi..")
                networkReporter.postValue(NetworkData(networkType,true))
                return
            }
            Log.i(Constants.DEBUG_TAG,"Device is not connected to data or wifi..")
            networkReporter.postValue(NetworkData(networkType,false))
        }
    }

    val networkState : LiveData<NetworkData> get() = networkReporter

}