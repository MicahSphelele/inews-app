package sphe.inews.domain.repository

import androidx.lifecycle.LiveData
import sphe.inews.domain.models.bookmark.Bookmark

interface BookmarkRepository {

    suspend fun insert(bookmark: Bookmark): Long

    suspend fun delete(bookmark: Bookmark): Int

    suspend fun getBooMarks(): List<Bookmark>

    //TODO(You need to change this function)
    fun getBooMarksObserved(): LiveData<List<Bookmark>>

    suspend fun getBooMark(url: String): Bookmark?

    suspend fun getBooMarksByCategory(category: String): List<Bookmark>?
}