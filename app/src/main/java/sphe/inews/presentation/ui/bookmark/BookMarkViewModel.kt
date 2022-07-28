package sphe.inews.presentation.ui.bookmark

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

@HiltViewModel
class BookMarkViewModel @Inject constructor(private val bookmarkRepository: BookmarkRepository) :
    ViewModel(), BookmarkRepository {

    override suspend fun insert(bookmark: Bookmark) {
         bookmarkRepository.insert(bookmark)
    }

    override suspend fun delete(bookmark: Bookmark): Int {
        return bookmarkRepository.delete(bookmark)
    }

    override fun getBooMarks(): Flow<List<Bookmark>?>? {
        return bookmarkRepository.getBooMarks()!!
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return bookmarkRepository.getBooMarksObserved()
    }

    override fun getBooMark(url: String): Flow<Bookmark?> {
        return bookmarkRepository.getBooMark(url)
    }

    override fun getBooMarksByCategory(category: String): Flow<List<Bookmark>?> {
        return bookmarkRepository.getBooMarksByCategory(category)
    }
}