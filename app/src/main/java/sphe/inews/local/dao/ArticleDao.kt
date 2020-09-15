package sphe.inews.local.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import sphe.inews.models.news.Article
import sphe.inews.util.Constants

@Dao
interface ArticleDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(article: Article)

    @Delete
    suspend fun delete(article: Article)

    @Query("SELECT * FROM ${Constants.TABLE_ARTICLE}")
    suspend fun getArticles() : LiveData<Article>

    @Query("SELECT * FROM ${Constants.TABLE_ARTICLE} WHERE ${Constants.URL} =:url")
    suspend fun getArticle(url : String) : Article

}