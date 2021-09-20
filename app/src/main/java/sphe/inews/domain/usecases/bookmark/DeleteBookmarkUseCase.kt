package sphe.inews.domain.usecases.bookmark

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

class DeleteBookmarkUseCase @Inject constructor(private val bookmarkRepository: BookmarkRepository) {

    operator fun invoke(scope: CoroutineScope, bookmark: Bookmark) {
        scope.launch {
            bookmarkRepository.delete(bookmark)
        }
    }
}