package sphe.inews.models.weather.forecast

import sphe.inews.models.weather.Current
import sphe.inews.models.weather.Location

data class WeatherForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)