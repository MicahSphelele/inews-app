package sphe.inews.domain.usecases.weather

import sphe.inews.domain.repository.WeatherRepository
import javax.inject.Inject

class GetWeatherForecastUseCase @Inject constructor(weatherRepository: WeatherRepository) {

    operator fun invoke(
        city: String,
        aqi: String? = "no"
    ) {

    }
}