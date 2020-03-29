package sphe.inews.models.news


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class NewsResponse(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)