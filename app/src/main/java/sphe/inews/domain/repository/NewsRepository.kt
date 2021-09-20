package sphe.inews.domain.repository

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.news.NewsResponse

interface NewsRepository {

     fun getNews(
        scope: CoroutineScope,
        country: String,
        category: String
    ): LiveData<NetworkResult<NewsResponse>>
}