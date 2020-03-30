package sphe.inews.models.covid


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class CovidResponse(
    var country: String,
    @SerializedName("latest_stat_by_country")
    var latestStatByCountry: List<LatestStatByCountry>?
)