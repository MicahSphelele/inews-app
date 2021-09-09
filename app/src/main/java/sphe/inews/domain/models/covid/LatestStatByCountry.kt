package sphe.inews.domain.models.covid


import com.google.gson.annotations.SerializedName
import androidx.annotation.Keep

@Keep
data class LatestStatByCountry(
    @SerializedName("active_cases")
    var activeCases: String,
    @SerializedName("country_name")
    var countryName: String,
    var id: String,
    @SerializedName("new_cases")
    var newCases: String,
    @SerializedName("new_deaths")
    var newDeaths: String,
    @SerializedName("record_date")
    var recordDate: String,
    var region: Any,
    @SerializedName("serious_critical")
    var seriousCritical: String,
    @SerializedName("total_cases")
    var totalCases: String,
    @SerializedName("total_cases_per1m")
    var totalCasesPer1m: String,
    @SerializedName("total_deaths")
    var totalDeaths: String,
    @SerializedName("total_recovered")
    var totalRecovered: String
)