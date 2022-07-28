package sphe.inews.data.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.flow.Flow
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.data.local.dao.BookmarkDao
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

class BookmarkRepositoryImpl @Inject constructor(private val bookmarkDao: BookmarkDao?) : BookmarkRepository {

    override suspend fun insert(bookmark: Bookmark) {
        return bookmarkDao?.insert(bookmark)!!
    }

    override suspend fun delete(bookmark: Bookmark): Int {
        return bookmarkDao?.delete(bookmark)!!
    }

    override fun getBooMarks(): Flow<List<Bookmark>> {
        return bookmarkDao?.getBooMarks()!!
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return bookmarkDao?.getBooMarksObserved()!!
    }

    override fun getBooMark(url: String): Flow<Bookmark?> {
        return bookmarkDao?.getBooMark(url)!!
    }

    override  fun getBooMarksByCategory(category: String): Flow<List<Bookmark>?> {
        return bookmarkDao?.getBooMarksByCategory(category)!!
    }
}