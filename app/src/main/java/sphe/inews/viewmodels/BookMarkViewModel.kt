package sphe.inews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.domain.repository.BookmarkRepository
import sphe.inews.data.repository.RealBookmarkRepository
import sphe.inews.domain.models.bookmark.Bookmark
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val realBookmarkRepo: RealBookmarkRepository) :
    ViewModel(), BookmarkRepository {

    override suspend fun insert(bookmark: Bookmark): Long {
        return realBookmarkRepo.insert(bookmark)
    }

    override suspend fun delete(bookmark: Bookmark): Int {
        return realBookmarkRepo.delete(bookmark)
    }

    override suspend fun getBooMarks(): List<Bookmark> {
        return realBookmarkRepo.getBooMarks()
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return realBookmarkRepo.getBooMarksObserved()
    }

    override suspend fun getBooMark(url: String): Bookmark? {
        return realBookmarkRepo.getBooMark(url)
    }

    override suspend fun getBooMarksByCategory(category: String): List<Bookmark>? {
        return realBookmarkRepo.getBooMarksByCategory(category)
    }
}