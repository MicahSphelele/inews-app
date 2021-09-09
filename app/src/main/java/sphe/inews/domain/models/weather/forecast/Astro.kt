package sphe.inews.domain.models.weather.forecast

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Astro(
    @SerializedName("moon_illumination")
    val moonIllumination: String,
    val moon_phase: String,
    val moonrise: String,
    @SerializedName("moonset")
    val moonSet: String,
    val sunrise: String,
    val sunset: String
)