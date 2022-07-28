package sphe.inews.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import kotlinx.coroutines.flow.Flow
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.util.Constants

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
   suspend fun insert(bookmark: Bookmark)

    @Delete
    fun delete(bookmark: Bookmark): Int

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK}")
    fun getBooMarksObserved(): LiveData<List<Bookmark>>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK}")
    fun getBooMarks(): Flow<List<Bookmark>>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK} WHERE ${Constants.URL} = :url")
    fun getBooMark(url: String): Flow<Bookmark>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK} WHERE ${Constants.CATEGORY} = :category")
    fun getBooMarksByCategory(category: String): Flow<List<Bookmark>?>

}