package sphe.inews.models.domain

import sphe.inews.models.Bookmark
import sphe.inews.models.news.Article
import sphe.inews.models.news.Source

class ArticleBookmarkMapper: DomainMapper<Bookmark,Article> {

    override fun mapToDomainModel(entity: Bookmark): Article {
        return Article(
            url = entity.url!!,
            author = entity.author!!,
            content = entity.content!!,
            description = entity.description!!,
            publishedAt = entity.publishedAt!!,
            source = Source(entity.sourceId!!, entity.sourceName!!),
            title = entity.title!!,
            urlToImage = entity.urlToImage!!)
    }

    fun toDomainList(initial: List<Bookmark>) : List<Article>{
        return initial.map { mapToDomainModel(it) }
    }

}