package sphe.inews.domain.usecases.news

import androidx.lifecycle.LiveData
import kotlinx.coroutines.CoroutineScope
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.domain.repository.NewsRepository
import javax.inject.Inject

class GetNewsByCategoryUseCase @Inject constructor(private val newsRepository: NewsRepository) {

    operator fun invoke(
        scope: CoroutineScope,
        country: String,
        category: String
    ): LiveData<NetworkResult<NewsResponse>> {
        return newsRepository.getNews(scope, country = country, category = category)
    }
}