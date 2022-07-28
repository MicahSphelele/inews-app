package sphe.inews.domain.usecases.bookmark

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import sphe.inews.domain.models.bookmark.Bookmark
import sphe.inews.domain.repository.BookmarkRepository
import javax.inject.Inject

class GetBookmarkByCategory @Inject constructor(private val bookmarkRepository: BookmarkRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        category: String,
        onBookmarks: (List<Bookmark>?) -> Unit
    ) {
        scope.launch {
            bookmarkRepository.getBooMarksByCategory(category).collect { bookmarks ->
                onBookmarks(bookmarks)
            }
        }
    }
}