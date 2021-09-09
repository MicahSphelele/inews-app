package sphe.inews.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.util.Constants

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark): Long

    @Delete
    suspend fun delete(bookmark: Bookmark): Int

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK}")
    fun getBooMarksObserved(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK}")
    suspend fun getBooMarks(): List<Bookmark>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK} WHERE ${Constants.URL} = :url")
    suspend fun getBooMark(url: String): Bookmark

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK} WHERE ${Constants.CATEGORY} = :category")
    suspend fun getBooMarksByCategory(category: String): List<Bookmark>

}