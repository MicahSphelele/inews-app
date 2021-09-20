package sphe.inews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository) :
    ViewModel(), BookmarkRepository {

    override suspend fun insert(bookmark: Bookmark): Long {
        return bookmarkRepository.insert(bookmark)
    }

    override suspend fun delete(bookmark: Bookmark): Int {
        return bookmarkRepository.delete(bookmark)
    }

    override suspend fun getBooMarks(): List<Bookmark> {
        return bookmarkRepository.getBooMarks()
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return bookmarkRepository.getBooMarksObserved()
    }

    override suspend fun getBooMark(url: String): Bookmark? {
        return bookmarkRepository.getBooMark(url)
    }

    override suspend fun getBooMarksByCategory(category: String): List<Bookmark>? {
        return bookmarkRepository.getBooMarksByCategory(category)
    }
}