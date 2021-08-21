package sphe.inews.network

import retrofit2.http.GET
import retrofit2.http.Query

import sphe.inews.models.weather.current.CurrentWeatherResponse
import sphe.inews.models.weather.forecast.WeatherForecastResponse

interface WeatherApi {

    @GET("current.json")
    suspend fun getCurrentWeather(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("aqi") aqi: String? = "no"
    ): CurrentWeatherResponse

    @GET("forecast.json")
    suspend fun getWeatherForecast(
        @Query("key") key: String,
        @Query("q") city: String,
        @Query("days") days: Int = 1,
        @Query("aqi") aqi: String = "no",
        @Query("alerts") alerts: String = "no"
    ): WeatherForecastResponse
}