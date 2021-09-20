package sphe.inews.data.repository

import io.reactivex.Flowable
import sphe.inews.data.network.Covid19Api
import sphe.inews.domain.models.covid.CovidResponse
import sphe.inews.domain.repository.Covid19Repository
import javax.inject.Inject

class RealCovid19Repository @Inject constructor(private val covid19Api: Covid19Api) : Covid19Repository {

    override fun getCovidStatsByCountry(country: String): Flowable<CovidResponse>? {
        return covid19Api.getCovidStatsByCountry(country)
    }
}