package sphe.inews.data.repository

import sphe.inews.data.network.INewsApi
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.domain.repository.NewsRepository
import sphe.inews.util.Constants
import javax.inject.Inject

class RealNewsRepository @Inject constructor(private val newsApi: INewsApi) : NewsRepository {
    
    override suspend fun getNews(country: String, category: String) : NewsResponse {
        return newsApi.getNews(String(Constants.PACKS_NEWS), country, category)
    }
}