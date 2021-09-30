package sphe.inews.data.local.storage

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import sphe.inews.domain.models.storage.Storage

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

    override fun saveIntData(key: String, data: Int) {
        sharedPrefs
            ?.edit()
            ?.putInt(key, data)
            ?.apply()
    }

    override fun getIntData(key: String): Int {
        return sharedPrefs?.getInt(key, 0)!!
    }
}