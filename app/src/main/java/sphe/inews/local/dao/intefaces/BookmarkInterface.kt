package sphe.inews.local.dao.intefaces

import androidx.lifecycle.LiveData
import sphe.inews.models.news.Article

interface BookmarkInterface {

   fun insert(article: Article)

   fun delete(article: Article)

   fun getBooMarks() : LiveData<Article>

   fun getBooMark(url : String) : Article
}