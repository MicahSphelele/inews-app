package sphe.inews.util

import android.app.Application
import java.text.SimpleDateFormat
import java.util.*

object Constants {

    const val BASE_NEWS_URL = "http://newsapi.org/v2/"
    const val BUSINESS = "business"
    const val HEALTH = "health"
    const val SPORTS = "sports"
    const val TECHNOLOGY = "technology"
    const val ENTERTAINMENT = "entertainment"
    const val NAMED_APP_VERSION="app_version"


   val  PACKS_NEWS = byteArrayOf(
        52,101,51,97,102,
        99,48,50,102,102,
        50,54,52,101,101,
        52,57,54,55,54,102,
        98,50,50,52,48,99,
        51,57,56,99,56
    )
    const val BASE_COVID_URL = "https://coronavirus-monitor.p.rapidapi.com/coronavirus/"

     val PACKS_COVID = byteArrayOf(
         97,97,53,57,102,
         53,98,101,101,101,
         109,115,104,99,50,
         55,97,99,50,48,
         102,99,53,52,48,
         54,102,50,112,49,
         101,55,51,55,55,
         106,115,110,102,98,
         102,102,48,57,48,
         51,49,51,101,48
     )

    fun appDateFormat(date:String) : String?{

        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS",Locale.getDefault()) //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'
        
        return SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(dateFormat.parse(date)!!)

    }

    fun appDateFormatArticle(date:String) : String?{
        //2020-04-10T07:44:43Z
        val dateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'",Locale.getDefault()) //yyyy-MM-dd'T'HH:mm:ss.SSS'Z'

        return SimpleDateFormat("yyyy-MM-dd HH:mm",Locale.getDefault()).format(dateFormat.parse(date)!!)

    }

    fun launchCustomIntent(application:Application,url:String){

    }

    //https://stackoverflow.com/questions/55748235/kotlin-check-for-words-in-string

}