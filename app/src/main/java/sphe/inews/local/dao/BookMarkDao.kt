package sphe.inews.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import sphe.inews.models.news.Article
import sphe.inews.util.Constants

@Dao
interface BookMarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK}")
    suspend fun getBooMarks() : LiveData<Article>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK} WHERE ${Constants.URL} =:url")
    suspend fun getBooMark(url : String) : Article

}