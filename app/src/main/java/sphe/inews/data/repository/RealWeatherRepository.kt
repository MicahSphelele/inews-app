package sphe.inews.data.repository

import sphe.inews.data.network.WeatherApi
import sphe.inews.domain.models.weather.current.CurrentWeatherResponse
import sphe.inews.domain.models.weather.forecast.WeatherForecastResponse
import sphe.inews.domain.repository.WeatherRepository
import javax.inject.Inject

class RealWeatherRepository @Inject constructor(private val weatherApi: WeatherApi) : WeatherRepository {

    override suspend fun getCurrentWeather(city: String, aqi: String?): CurrentWeatherResponse {
        return weatherApi.getCurrentWeather(city = city, aqi = aqi)
    }

    override suspend fun getWeatherForecast(
        city: String,
        days: Int,
        aqi: String,
        alerts: String
    ): WeatherForecastResponse {
        return weatherApi.getWeatherForecast(city = city, days = days, aqi = aqi, alerts = alerts)
    }
}