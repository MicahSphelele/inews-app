package sphe.inews.util

object Constants {

    const val BASE_URL = "http://newsapi.org/v2/"
    const val BUSINESS = "business"
    const val HEALTH = "health"
    const val SPORT = "sport"
    const val TECHNOLOGY = "technology"
    const val ENTERTAINMENT = "entertainment"

   val  PACKS = byteArrayOf(
        52,101,51,97,102,
        99,48,50,102,102,
        50,54,52,101,101,
        52,57,54,55,54,102,
        98,50,50,52,48,99,
        51,57,56,99,56
    )
    //https://stackoverflow.com/questions/55748235/kotlin-check-for-words-in-string
    val KEY_WORDS = listOf("covid-19","shutdown","services")
}