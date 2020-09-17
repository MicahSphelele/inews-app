package sphe.inews.local.dao.intefaces

import androidx.lifecycle.LiveData
import sphe.inews.models.Bookmark

interface BookmarkInterface {

   fun insert(bookmark: Bookmark)

   fun delete(bookmark: Bookmark)

   fun getBooMarks() : LiveData<Bookmark>

   fun getBooMark(url : String) : Bookmark
}