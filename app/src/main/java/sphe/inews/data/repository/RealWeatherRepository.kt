package sphe.inews.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import retrofit2.HttpException
import sphe.inews.data.network.WeatherApi
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.weather.current.CurrentWeatherResponse
import sphe.inews.domain.models.weather.forecast.WeatherForecastResponse
import sphe.inews.domain.repository.WeatherRepository
import sphe.inews.util.AppLogger
import java.io.IOException
import javax.inject.Inject

class RealWeatherRepository @Inject constructor(private val weatherApi: WeatherApi) :
    WeatherRepository {

    private var currentWeatherResponse: MediatorLiveData<NetworkResult<CurrentWeatherResponse>> =
        MediatorLiveData()

    private var weatherForecastResponse: MediatorLiveData<NetworkResult<WeatherForecastResponse>> =
        MediatorLiveData()

    private fun getCurrentWeatherFlow(
        city: String,
        aqi: String?
    ): Flow<CurrentWeatherResponse> = flow {
        emit(weatherApi.getCurrentWeather(city = city, aqi = aqi))
    }

    private fun getWeatherForecastFlow(
        city: String,
        days: Int,
        aqi: String,
        alerts: String
    ): Flow<WeatherForecastResponse> = flow {
        emit(
            weatherApi.getWeatherForecast(
                city = city,
                days = days,
                aqi = aqi,
                alerts = alerts
            )
        )
    }

    override fun getCurrentWeather(
        scope: CoroutineScope,
        city: String,
        aqi: String?
    ): LiveData<NetworkResult<CurrentWeatherResponse>> {

        scope.launch {
            try {
                getCurrentWeatherFlow(city, aqi)
                    .flowOn(Dispatchers.IO)
                    .onStart {
                        currentWeatherResponse.value = NetworkResult.Loading()
                    }.catch {
                        weatherForecastResponse.value = NetworkResult.Error(
                            message = "Something went wrong.",
                            data = null
                        )
                    }.map {
                        it
                    }.collect {
                        currentWeatherResponse.value = NetworkResult.Success(it)
                    }
            } catch (ex: HttpException) {
                AppLogger.error(ex)
                currentWeatherResponse.value = NetworkResult.Error(
                    message = "An unexpected error occurred.",
                    data = null
                )
            } catch (ex: IOException) {
                currentWeatherResponse.value = NetworkResult.Error(
                    message = "Couldn't reach server. Check internet connection.",
                    data = null
                )
            }
        }

        return currentWeatherResponse
    }

    override fun getWeatherForecast(
        scope: CoroutineScope,
        city: String,
        days: Int,
        aqi: String,
        alerts: String
    ): LiveData<NetworkResult<WeatherForecastResponse>> {

        scope.launch {
            try {
                getWeatherForecastFlow(city, days, aqi, alerts)
                    .flowOn(Dispatchers.IO)
                    .onStart {
                        weatherForecastResponse.value = NetworkResult.Loading()
                    }.catch {
                        weatherForecastResponse.value = NetworkResult.Error(
                            message = "Something went wrong.",
                            data = null
                        )
                    }.map {
                        it
                    }.collect {
                        weatherForecastResponse.value = NetworkResult.Success(it)
                    }
            } catch (ex: HttpException) {
                AppLogger.error(ex)
                weatherForecastResponse.value = NetworkResult.Error(
                    message = "An unexpected error occurred.",
                    data = null
                )
            } catch (ex: IOException) {
                weatherForecastResponse.value = NetworkResult.Error(
                    message = "Couldn't reach server. Check internet connection.",
                    data = null
                )
            }
        }

        return weatherForecastResponse
    }
}