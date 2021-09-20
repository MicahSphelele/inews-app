package sphe.inews.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.weather.current.CurrentWeatherResponse
import sphe.inews.domain.models.weather.forecast.WeatherForecastResponse

interface WeatherRepository {

    fun getCurrentWeather(
        scope: CoroutineScope,
        city: String,
        aqi: String? = "no"
    ): LiveData<NetworkResult<CurrentWeatherResponse>>

    fun getWeatherForecast(
        scope: CoroutineScope,
        city: String,
        days: Int = 5,
        aqi: String = "no",
        alerts: String = "no"
    ): LiveData<NetworkResult<WeatherForecastResponse>>
}