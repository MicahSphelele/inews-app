package sphe.inews.domain.usecases.bookmark

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkByCatUseCase @Inject constructor(private val bookmarkRepository: BookmarkRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        category: String,
        onBookmarks: (List<Bookmark>?) -> Unit
    ) {
        scope.launch {
            val bookmarks = bookmarkRepository.getBooMarksByCategory(category)
            onBookmarks(bookmarks)
        }
    }
}