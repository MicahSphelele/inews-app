package sphe.inews.local.repo

import androidx.lifecycle.LiveData
import sphe.inews.local.dao.intefaces.BookmarkInterface
import sphe.inews.models.news.Article

class BookmarkRepository : BookmarkInterface {

    override fun insert(article: Article) {
        TODO("Not yet implemented")
    }

    override fun delete(article: Article) {
        TODO("Not yet implemented")
    }

    override fun getBooMarks(): LiveData<Article> {
        TODO("Not yet implemented")
    }

    override fun getBooMark(url: String): Article {
        TODO("Not yet implemented")
    }
}