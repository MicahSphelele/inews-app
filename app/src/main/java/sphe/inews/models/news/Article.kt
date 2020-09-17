package sphe.inews.models.news


import androidx.annotation.Keep
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import sphe.inews.util.Constants

@Keep
@Entity(tableName = Constants.TABLE_BOOKMARK)
data class Article(
    @PrimaryKey
    @SerializedName("url")
    @ColumnInfo(name = Constants.URL)
    var url: String,
    @SerializedName("author")
    @ColumnInfo(name = Constants.AUTHOR)
    var author: String,
    @SerializedName("content")
    @ColumnInfo(name = Constants.CONTENT)
    var content: String,
    @SerializedName("description")
    @ColumnInfo(name = Constants.DESC)
    var description: String,
    @SerializedName("publishedAt")
    @ColumnInfo(name = Constants.PUBLISH_AT)
    var publishedAt: String,
    @SerializedName("source")
    @ColumnInfo(name = Constants.SOURCE)
    var source: Source,
    @SerializedName("title")
    @ColumnInfo(name = Constants.TITLE)
    var title: String,
    @SerializedName("urlToImage")
    @ColumnInfo(name = Constants.IMAGE_URL)
    var urlToImage: String
)