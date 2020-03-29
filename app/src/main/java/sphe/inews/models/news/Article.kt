package sphe.inews.models.news


import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Article(
    @SerializedName("author")
    var author: String,
    @SerializedName("content")
    var content: String,
    @SerializedName("description")
    var description: String,
    @SerializedName("publishedAt")
    var publishedAt: String,
    @SerializedName("source")
    var source: Source,
    @SerializedName("title")
    var title: String,
    @SerializedName("url")
    var url: String,
    @SerializedName("urlToImage")
    var urlToImage: String
)