package sphe.inews.local.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.local.dao.intefaces.BookmarkInterface
import sphe.inews.local.repo.BookmarkRepository
import sphe.inews.domain.models.bookmark.Bookmark
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val bookmarkRepo: BookmarkRepository) :
    ViewModel(), BookmarkInterface {

    override suspend fun insert(bookmark: Bookmark): Long {
        return bookmarkRepo.insert(bookmark)
    }

    override suspend fun delete(bookmark: Bookmark): Int {
        return bookmarkRepo.delete(bookmark)
    }

    override suspend fun getBooMarks(): List<Bookmark> {
        return bookmarkRepo.getBooMarks()
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return bookmarkRepo.getBooMarksObserved()
    }

    override suspend fun getBooMark(url: String): Bookmark? {
        return bookmarkRepo.getBooMark(url)
    }

    override suspend fun getBooMarksByCategory(category: String): List<Bookmark>? {
        return bookmarkRepo.getBooMarksByCategory(category)
    }
}