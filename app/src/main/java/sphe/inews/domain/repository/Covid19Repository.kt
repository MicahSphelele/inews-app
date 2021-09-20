package sphe.inews.domain.repository

import io.reactivex.Flowable
import retrofit2.http.Query
import sphe.inews.domain.models.covid.CovidResponse

interface Covid19Repository {

    fun getCovidStatsByCountry(@Query("country") country: String): Flowable<CovidResponse>?
}