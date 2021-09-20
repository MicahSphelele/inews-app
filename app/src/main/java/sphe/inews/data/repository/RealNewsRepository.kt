package sphe.inews.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sphe.inews.data.network.INewsApi
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.domain.repository.NewsRepository
import sphe.inews.util.Constants
import javax.inject.Inject

class RealNewsRepository @Inject constructor(private val newsApi: INewsApi) : NewsRepository {

    private var newsResponse: MediatorLiveData<NetworkResult<NewsResponse>> = MediatorLiveData()

    private fun getNewsFlow(country: String, category: String): Flow<NewsResponse> = flow {
        emit(newsApi.getNews(country, category, String(Constants.PACKS_NEWS)))
    }

    override fun getNews(
        scope: CoroutineScope,
        country: String,
        category: String
    ): LiveData<NetworkResult<NewsResponse>> {
        scope.launch {
            try {
                getNewsFlow(country, category)
                    .flowOn(Dispatchers.IO)
                    .onStart {
                        newsResponse.value = NetworkResult.Loading()
                    }.map {
                        it
                    }.collect {
                        newsResponse.value = NetworkResult.Success(data = it)
                    }
            } catch (ex: Exception) {
                newsResponse.value = NetworkResult.Error(
                    message = "Something went wrong: ${ex.message}",
                    data = null
                )
            }
        }
        return newsResponse
    }

}