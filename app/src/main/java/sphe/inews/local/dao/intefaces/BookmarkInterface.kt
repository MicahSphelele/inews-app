package sphe.inews.local.dao.intefaces

import androidx.lifecycle.LiveData
import sphe.inews.models.Bookmark

interface BookmarkInterface {

   fun insert(bookmark: Bookmark) : Long

   fun delete(bookmark: Bookmark) : Int

   fun getBooMarks() : LiveData<List<Bookmark>>

   fun getBooMark(url : String) : Bookmark?
}