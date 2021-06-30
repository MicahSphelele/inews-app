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

    override suspend fun insert(bookmark: Bookmark) : Long {
        return bookmarkDao?.insert(bookmark)!!
    }

    override suspend fun delete(bookmark: Bookmark) : Int {
       return bookmarkDao?.delete(bookmark)!!
    }

    override suspend fun getBooMarks(): List<Bookmark> {
        return bookmarkDao?.getBooMarks()!!
    }

    override fun getBooMarksObserved(): LiveData<List<Bookmark>> {
        return bookmarkDao!!.getBooMarksObserved()
    }

    override suspend fun getBooMark(url: String): Bookmark? {
      return bookmarkDao?.getBooMark(url)
    }

}