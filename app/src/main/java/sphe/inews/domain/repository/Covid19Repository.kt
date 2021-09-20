package sphe.inews.domain.repository

import androidx.lifecycle.LiveData
import retrofit2.http.Query
import sphe.inews.domain.Resources
import sphe.inews.domain.models.covid.CovidResponse

interface Covid19Repository {

    fun getCovidStatsByCountry(@Query("country") country: String): LiveData<Resources<CovidResponse>>?
}