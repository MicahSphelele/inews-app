package sphe.inews.local.repo

import android.app.Application
import androidx.lifecycle.LiveData
import sphe.inews.local.dao.BookmarkDao
import sphe.inews.local.dao.intefaces.BookmarkInterface
import sphe.inews.local.room.AppDB
import sphe.inews.models.Bookmark
import javax.inject.Inject

class BookmarkRepository @Inject constructor(application: Application) : BookmarkInterface {

    private var bookmarkDao : BookmarkDao ? = null

    init {
        bookmarkDao = AppDB.getInstance(application).bookmarkDao()
    }

    override fun insert(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override fun delete(bookmark: Bookmark) {
        TODO("Not yet implemented")
    }

    override fun getBooMarks(): LiveData<Bookmark> {
        TODO("Not yet implemented")
    }

    override fun getBooMark(url: String): Bookmark {
        TODO("Not yet implemented")
    }


}