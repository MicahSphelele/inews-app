package sphe.inews.ui.main.dialogfragments.covid

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import sphe.inews.domain.models.covid.CovidResponse
import sphe.inews.network.Covid19Api
import sphe.inews.network.results.Resources
import javax.inject.Inject

@HiltViewModel
class Covid19StatsViewModel @Inject constructor(private var api: Covid19Api) : ViewModel() {

    private var covidResponse: MediatorLiveData<Resources<CovidResponse>>? = null

    fun observeCovid19Data(country: String): LiveData<Resources<CovidResponse>>? {

        covidResponse = MediatorLiveData<Resources<CovidResponse>>()

        covidResponse?.let {
            covidResponse?.value = Resources.loading(
                CovidResponse("", null)
            )
        }

        val source: LiveData<Resources<CovidResponse>?> =
            LiveDataReactiveStreams.fromPublisher(
                api.getCovidStatsByCountry(country)
                    ?.onErrorReturn { throwable ->
                        throwable.stackTrace
                        Log.e("@Covid19StatsViewModel", "apply error: $throwable")

                        return@onErrorReturn CovidResponse("", null)
                    }
                    ?.map(object :
                        Function<CovidResponse, Resources<CovidResponse>?> {
                        @Throws(Exception::class)
                        override fun apply(response: CovidResponse): Resources<CovidResponse>? {
                            Log.d("@Covid19StatsViewModel", "apply data")
                            response.let {
                                if (response.latestStatByCountry.isNullOrEmpty()) {
                                    return Resources.error("Something went wrong", null)
                                }
                                return Resources.success(response)
                            }
                        }
                    })!!.subscribeOn(Schedulers.io())
            )

        covidResponse?.let {
            covidResponse?.addSource(source) { response ->
                covidResponse.apply {
                    this?.value = response
                    this?.removeSource(source)
                }
            }
        }


        return covidResponse
    }
}