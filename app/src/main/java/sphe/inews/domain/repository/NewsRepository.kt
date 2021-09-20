package sphe.inews.domain.repository

import sphe.inews.domain.models.news.NewsResponse

interface NewsRepository {

    suspend fun getNews(
        country: String,
        category: String
    ) : NewsResponse
}