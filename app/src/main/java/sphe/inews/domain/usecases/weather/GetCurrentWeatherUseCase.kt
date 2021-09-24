package sphe.inews.domain.usecases.weather

import kotlinx.coroutines.CoroutineScope
import sphe.inews.domain.repository.WeatherRepository
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(weatherRepository: WeatherRepository) {

    operator fun invoke(scope: CoroutineScope,
                        city: String,
                        aqi: String? = "no") {
        
    }

}