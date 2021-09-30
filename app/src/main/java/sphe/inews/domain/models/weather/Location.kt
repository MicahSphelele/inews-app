package sphe.inews.domain.models.weather

import com.google.gson.annotations.SerializedName

data class Location(
    val country: String,
    @SerializedName("lat")
    val latitude: Double,
    val localtime: String,
    @SerializedName("localtime_epoch") //
    val localTimeInEPOCH: Int,
    @SerializedName("lon")
    val longitude: Double,
    val name: String,
    val region: String,
    @SerializedName("tz_id")
    val timeZoneId: String
)