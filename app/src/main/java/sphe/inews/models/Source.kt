package sphe.inews.models


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Source(
    @SerializedName("id")
    var id: Any,
    @SerializedName("name")
    var name: String
)