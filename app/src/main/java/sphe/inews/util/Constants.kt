package sphe.inews.util

import android.app.Activity
import android.net.Uri
import androidx.browser.customtabs.CustomTabColorSchemeParams
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import sphe.inews.R
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val DEBUG_TAG = "@InewsDebug"
    const val BASE_NEWS_URL = "http://newsapi.org/v2/"
    const val BASE_COVID_URL = "https://coronavirus-monitor.p.rapidapi.com/coronavirus/"
    const val BASE_WEATHER_API_URL = "https://api.weatherapi.com/v1/"
    const val NAMED_APP_VERSION = "app_version"
    const val NAMED_SET_OLD = "constraint_set_old"
    const val NAMED_SET_NEW = "constraint_set_new"
    const val NAMED_COUNTRIES = "countries"
    const val NAMED_CATEGORIES = "categories"
    const val NAMED_COVID_19 = "covid19"
    const val NAMED_WEATHER_API = "weatherAPI"
    const val NAMED_STORAGE = "storage"
    const val NAMED_IS_ON_TEST_MODE = "isOnTestMode"
    const val KEY_THEME = "appTheme"
    const val DEFAULT_THEME = "system_default"

    const val TABLE_BOOKMARK = "bookmark"
    const val AUTHOR = "author"
    const val CONTENT = "content"
    const val DESC = "description"
    const val PUBLISH_AT = "publishedAt"
    const val SOURCE_ID = "sourceId"
    const val SOURCE_NAME = "sourceName"
    const val TITLE = "title"
    const val URL = "url"
    const val IMAGE_URL = "urlToImage"
    const val CATEGORY = "category"

    const val DB_VERSION = 2
    const val LOCATION_REQUEST_CODE = 1000
    const val LOCATION_SETTINGS_CODE = 1001

    val PACKS_NEWS = byteArrayOf(
        52, 101, 51, 97, 102,
        99, 48, 50, 102, 102,
        50, 54, 52, 101, 101,
        52, 57, 54, 55, 54, 102,
        98, 50, 50, 52, 48, 99,
        51, 57, 56, 99, 56
    )


    val PACKS_COVID = byteArrayOf(
        97, 97, 53, 57, 102,
        53, 98, 101, 101, 101,
        109, 115, 104, 99, 50,
        55, 97, 99, 50, 48,
        102, 99, 53, 52, 48,
        54, 102, 50, 112, 49,
        101, 55, 51, 55, 55,
        106, 115, 110, 102, 98,
        102, 102, 48, 57, 48,
        51, 49, 51, 101, 48
    )

    fun appDateFormat(date: String): String? {

        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss.SSS",
            Locale.getDefault()
        ) //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
            dateFormat.parse(
                date
            )!!
        )

    }

    fun appDateFormatArticle(date: String): String? {
        //2020-04-10T07:44:43Z
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd'T'HH:mm:ss'Z'",
            Locale.getDefault()
        ) //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

        return SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault()).format(
            dateFormat.parse(
                date
            )!!
        )

    }

    fun launchCustomTabIntent(activity: Activity, url: String) {
        val intentBuilder = CustomTabsIntent.Builder()
        val customTabColorSchemeParams = CustomTabColorSchemeParams.Builder()
        customTabColorSchemeParams
            .setToolbarColor(ContextCompat.getColor(activity, R.color.colorAccent))
        intentBuilder.setDefaultColorSchemeParams(customTabColorSchemeParams.build())
        val customTabsIntent = intentBuilder.build()
        activity.let {  customTabsIntent.launchUrl(it, Uri.parse(url)) }
    }
}