package sphe.inews.local.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import sphe.inews.local.dao.intefaces.BookmarkInterface
import sphe.inews.models.Bookmark

class BookMarkViewModel : ViewModel(), BookmarkInterface {

    override fun insert(bookmark: Bookmark): Long {
        TODO("Not yet implemented")
    }

    override fun delete(bookmark: Bookmark): Int {
        TODO("Not yet implemented")
    }

    override fun getBooMarks(): LiveData<Bookmark> {
        TODO("Not yet implemented")
    }

    override fun getBooMark(url: String): Bookmark {
        TODO("Not yet implemented")
    }

}