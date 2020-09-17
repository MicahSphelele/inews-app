package sphe.inews.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import sphe.inews.models.Bookmark
import sphe.inews.util.Constants

@Dao
interface BookmarkDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(bookmark: Bookmark)

    @Delete
    suspend fun delete(bookmark: Bookmark)

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK}")
     fun getBooMarks() : LiveData<Bookmark>

    @Query("SELECT * FROM ${Constants.TABLE_BOOKMARK} WHERE ${Constants.URL} = :url")
    suspend fun getBooMark(url : String) : Bookmark

}