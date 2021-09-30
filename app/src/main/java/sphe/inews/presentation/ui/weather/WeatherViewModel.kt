package sphe.inews.presentation.ui.weather

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.weather.forecast.WeatherForecastResponse
import sphe.inews.domain.usecases.weather.GetWeatherForecastUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(private val getWeatherForecastUseCase: GetWeatherForecastUseCase) :
    ViewModel() {

    fun getWeatherForecast(
        city: String,
        days: Int = 5,
        aqi: String = "no",
        alerts: String = "no"
    ): LiveData<NetworkResult<WeatherForecastResponse>> {
        return getWeatherForecastUseCase(viewModelScope, city, days, aqi, alerts)
    }
}