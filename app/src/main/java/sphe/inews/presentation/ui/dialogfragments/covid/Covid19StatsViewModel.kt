package sphe.inews.presentation.ui.dialogfragments.covid

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.domain.Resources
import sphe.inews.domain.models.covid.CovidResponse
import sphe.inews.domain.usecases.covid19.GetCovidStatsUseCase
import javax.inject.Inject

@HiltViewModel
class Covid19StatsViewModel @Inject constructor(private val getCovidStatsUseCase: GetCovidStatsUseCase) : ViewModel() {

    fun observeCovid19Data(country: String): LiveData<Resources<CovidResponse>>? {

        return getCovidStatsUseCase(country)
    }
}