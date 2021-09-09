package sphe.inews.domain.models.weather.forecast

import sphe.inews.domain.models.weather.Current
import sphe.inews.domain.models.weather.Location

data class WeatherForecastResponse(
    val current: Current,
    val forecast: Forecast,
    val location: Location
)