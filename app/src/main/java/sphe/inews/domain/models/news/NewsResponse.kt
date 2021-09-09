package sphe.inews.domain.models.news


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName
import sphe.inews.domain.models.news.Article

@Keep
data class NewsResponse(
    @SerializedName("articles")
    var articles: List<Article>?,
    @SerializedName("status")
    var status: String?,
    @SerializedName("totalResults")
    var totalResults: Int?
)