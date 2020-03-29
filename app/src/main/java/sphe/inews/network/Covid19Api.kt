package sphe.inews.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query
import sphe.inews.models.covid.CovidResponse
import sphe.inews.util.Constants

interface Covid19Api {

    @Headers(
        "x-rapidapi-host: coronavirus-monitor.p.rapidapi.com",
        "x-rapidapi-key : ${Constants.PACKS_COVID}")
    @GET("latest_stat_by_country.php")
    fun getCovidStatsByCountry(@Query("country") country: String): Flowable<CovidResponse>?
}