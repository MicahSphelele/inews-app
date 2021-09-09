package sphe.inews.domain.models.weather.forecast

import com.google.gson.annotations.SerializedName

data class ForecastDay(
    val astro: Astro,
    val date: String,
    @SerializedName("date_epoch")
    val dateEPOCH: Int,
    val day: Day,
    val hour: List<Hour>
)