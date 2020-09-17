package sphe.inews.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import sphe.inews.util.Constants

@Entity(tableName = Constants.TABLE_BOOKMARK)
data class Bookmark(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = Constants.ID)
    var id: Int,
    @ColumnInfo(name = Constants.URL)
    var url: String,
    @ColumnInfo(name = Constants.AUTHOR)
    var author: String,
    @ColumnInfo(name = Constants.CONTENT)
    var content: String,
    @ColumnInfo(name = Constants.DESC)
    var description: String = "",
    @ColumnInfo(name = Constants.PUBLISH_AT)
    var publishedAt: String,
    @ColumnInfo(name = Constants.SOURCE_ID)
    var sourceId: String = "",
    @ColumnInfo(name = Constants.SOURCE_NAME)
    var sourceName: String = "",
    @ColumnInfo(name = Constants.TITLE)
    var title: String,
    @ColumnInfo(name = Constants.IMAGE_URL)
    var urlToImage: String = ""
)