package sphe.inews.data.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import sphe.inews.domain.models.covid.CovidResponse

interface Covid19Api {

    @GET("latest_stat_by_country.php")//latest_stat_by_country_name.php
    fun getCovidStatsByCountry(@Query("country") country: String): Flowable<CovidResponse>?

}