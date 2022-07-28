package sphe.inews.domain.usecases.bookmark

import androidx.lifecycle.LiveData
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkLiveData @Inject constructor(private val bookmarkRepository: BookmarkRepository) {

    operator fun invoke(): LiveData<List<Bookmark>> {
        return bookmarkRepository.getBooMarksObserved()
    }
}