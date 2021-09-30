package sphe.inews.domain.usecases.weather

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.weather.current.CurrentWeatherResponse
import sphe.inews.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(private val weatherRepository: WeatherRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        city: String,
        aqi: String? = "no"
    ) : LiveData<NetworkResult<CurrentWeatherResponse>>
    {
        return weatherRepository.getCurrentWeather(scope, city = city, aqi = aqi)
    }

}