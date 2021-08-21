package sphe.inews.models.weather.current

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import sphe.inews.models.weather.Current
import sphe.inews.models.weather.Location

@Keep
data class CurrentWeatherResponse(
    @SerializedName("current")
    val current: Current,
    @SerializedName("location")
    val location: Location
)