package sphe.inews.domain.repository

import sphe.inews.domain.models.weather.current.CurrentWeatherResponse
import sphe.inews.domain.models.weather.forecast.WeatherForecastResponse

interface WeatherRepository {

    suspend fun getCurrentWeather(
        city: String,
        aqi: String? = "no"
    ): CurrentWeatherResponse

    suspend fun getWeatherForecast(
         city: String,
         days: Int = 5,
         aqi: String = "no",
        alerts: String = "no"
    ): WeatherForecastResponse
}