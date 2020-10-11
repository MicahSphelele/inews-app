package sphe.inews.local.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sphe.inews.local.dao.intefaces.BookmarkInterface
import sphe.inews.local.repo.BookmarkRepository
import sphe.inews.models.Bookmark
import javax.inject.Inject

class BookMarkViewModel @Inject constructor(private val bookmarkRepo: BookmarkRepository) : ViewModel(), BookmarkInterface {

    override fun insert(bookmark: Bookmark): Long {
        return bookmarkRepo.insert(bookmark)
    }

    override fun delete(bookmark: Bookmark): Int {
        return bookmarkRepo.delete(bookmark)
    }

    override fun getBooMarks(): LiveData<Bookmark> {
        return bookmarkRepo.getBooMarks()
    }

    override fun getBooMark(url: String): Bookmark? {
        return bookmarkRepo.getBooMark(url)
    }

}