package sphe.inews.local.repo

import android.app.Application
import androidx.lifecycle.LiveData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
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

    override fun insert(bookmark: Bookmark) : Long {
        var insert : Long? = null
        runBlocking {
            launch(Dispatchers.Default){
                val job = async {bookmarkDao?.insert(bookmark)}
                insert = job.await()
            }
        }
        return insert!!
    }

    override fun delete(bookmark: Bookmark) : Long {
        var delete : Long? = null
        runBlocking {
            launch(Dispatchers.Default){
                val job = async {bookmarkDao?.delete(bookmark)}
                delete = job.await()
            }

        }
        return delete!!
    }

    override fun getBooMarks(): LiveData<Bookmark> {
        return bookmarkDao!!.getBooMarks()
    }

    override fun getBooMark(url: String): Bookmark {
        var bookmark : Bookmark? = null
        runBlocking {
            launch(Dispatchers.Default){
                val job = async {bookmarkDao?.getBooMark(url)}
                bookmark = job.await()
            }
        }
        return bookmark!!
    }


}