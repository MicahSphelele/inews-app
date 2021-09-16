package sphe.inews.domain.repository

import androidx.lifecycle.LiveData
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.interfaces.IBookmarkRepository
import sphe.inews.local.dao.BookmarkDao
import javax.inject.Inject

class BookmarkRepository @Inject constructor(private val bookmarkDao: BookmarkDao?) : IBookmarkRepository {

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