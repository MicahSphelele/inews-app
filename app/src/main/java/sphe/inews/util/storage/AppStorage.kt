package sphe.inews.util.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences

class AppStorage(application: Application) : Storage {

    private var sharedPrefs: SharedPreferences? = null

    init {
        sharedPrefs = application.getSharedPreferences("inews_data_prefs", Context.MODE_PRIVATE)
    }


    override fun saveStringData(key: String, data: String) {
        sharedPrefs
            ?.edit()
            ?.putString(key, data)
            ?.apply()
    }

    override fun getStringData(key: String, default: String): String {
        return sharedPrefs?.getString(key, default)!!
    }
}