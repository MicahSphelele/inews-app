package sphe.inews.domain.usecases.weather

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.weather.forecast.WeatherForecastResponse
import sphe.inews.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        city: String,
        days: Int,
        aqi: String = "no",
        alerts: String = "no"

    ): LiveData<NetworkResult<WeatherForecastResponse>> {

        return weatherRepository.getWeatherForecast(
            scope,
            city = city,
            days = days,
            aqi = aqi,
            alerts = alerts
        )
    }
}