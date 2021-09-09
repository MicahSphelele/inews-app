package sphe.inews.domain.models.weather

import com.google.gson.annotations.SerializedName

data class Current(
    val cloud: Int,
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelsLikeCelsius: Double,
    @SerializedName("feelslike_f")
    val feelsLikeFahrenheit: Double,
    @SerializedName("gust_kph")
    val gustOfWindKMP: Double,
    @SerializedName("gust_mph")
    val gustOfWindMPH: Double,
    val humidity: Int,
    @SerializedName("is_day")
    val showNightIcon: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("last_updated_epoch")
    val lastUpdatedUnixTime: Int,
    @SerializedName("precip_in")
    val precipInches: Double,
    @SerializedName("precip_mm")
    val precipMillimeters: Double,
    @SerializedName("pressure_in")
    val pressureInches: Double,
    @SerializedName("pressure_mb")
    val pressureMillibars: Double,
    @SerializedName("temp_c")
    val tempInCelsius: Double,
    @SerializedName("temp_f")
    val tempInFahrenheit: Double,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("vis_km")
    val visibilityInKilometer: Double,
    @SerializedName("vis_miles")
    val visibilityInMiles: Double,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDirection: String,
    @SerializedName("wind_kph")
    val windSpeedKPH: Double,
    @SerializedName("wind_mph")
    val windSpeedMPH: Double
)