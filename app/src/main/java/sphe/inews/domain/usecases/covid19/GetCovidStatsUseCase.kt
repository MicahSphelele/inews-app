package sphe.inews.domain.usecases.covid19

import androidx.lifecycle.LiveData
import sphe.inews.domain.Resources
import sphe.inews.domain.models.covid.CovidResponse
import sphe.inews.domain.repository.Covid19Repository
import javax.inject.Inject

class GetCovidStatsUseCase @Inject constructor(private val covid19Repository: Covid19Repository)  {
    
    operator fun invoke(country: String) : LiveData<Resources<CovidResponse>>? {
        return covid19Repository.getCovidStatsByCountry(country)
    }
}
