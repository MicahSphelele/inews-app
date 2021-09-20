package sphe.inews.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import sphe.inews.data.network.Covid19Api
import sphe.inews.domain.Resources
import sphe.inews.domain.models.covid.CovidResponse
import sphe.inews.domain.repository.Covid19Repository
import javax.inject.Inject

class RealCovid19Repository @Inject constructor(private val covid19Api: Covid19Api) : Covid19Repository {

    private var covidResponse: MediatorLiveData<Resources<CovidResponse>>? = null

    override fun getCovidStatsByCountry(country: String): LiveData<Resources<CovidResponse>>? {
        covidResponse?.let {
            covidResponse?.value = Resources.loading(
                CovidResponse("", null)
            )
        }
        val source: LiveData<Resources<CovidResponse>?> =
            LiveDataReactiveStreams.fromPublisher(
                covid19Api.getCovidStatsByCountry(country)
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