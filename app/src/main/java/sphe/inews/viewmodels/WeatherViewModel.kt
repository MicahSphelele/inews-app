package sphe.inews.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.domain.usecases.weather.GetCurrentWeatherUseCase
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(getWeatherForecastUseCase: GetCurrentWeatherUseCase) : ViewModel() {
}