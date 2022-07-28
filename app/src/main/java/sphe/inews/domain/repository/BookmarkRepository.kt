package sphe.inews.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import sphe.inews.domain.models.bookmark.Bookmark

interface BookmarkRepository {

    suspend fun insert(bookmark: Bookmark)

    suspend fun delete(bookmark: Bookmark): Int

    fun getBooMarks(): Flow<List<Bookmark>?>?

    fun getBooMarksObserved(): LiveData<List<Bookmark>>

    fun getBooMark(url: String): Flow<Bookmark?>

    fun getBooMarksByCategory(category: String): Flow<List<Bookmark>?>
}