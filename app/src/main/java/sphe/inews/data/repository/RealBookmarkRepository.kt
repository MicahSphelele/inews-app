package sphe.inews.data.repository

import androidx.lifecycle.LiveData
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.data.local.dao.BookmarkDao
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

class RealBookmarkRepository @Inject constructor(private val bookmarkDao: BookmarkDao?) :
    BookmarkRepository {

    override suspend fun insert(bookmark: Bookmark): Long {
        return bookmarkDao?.insert(bookmark)!!
    }

    override suspend fun delete(bookmark: Bookmark): Int {
        return bookmarkDao?.delete(bookmark)!!
    }

    override suspend fun getBooMarks(): List<Bookmark> {
        return bookmarkDao?.getBooMarks()!!
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return bookmarkDao!!.getBooMarksObserved()
    }

    override suspend fun getBooMark(url: String): Bookmark? {
        return bookmarkDao?.getBooMark(url)
    }

    override suspend fun getBooMarksByCategory(category: String): List<Bookmark>? {
        return bookmarkDao?.getBooMarksByCategory(category)
    }
}