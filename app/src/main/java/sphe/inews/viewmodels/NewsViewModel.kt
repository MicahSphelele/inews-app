package sphe.inews.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import sphe.inews.models.news.NewsResponse
import sphe.inews.network.results.NetworkResult
import sphe.inews.network.INewsApi
import sphe.inews.util.Constants
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private var newsApi: INewsApi) : ViewModel() {

    private var newsResponse: MediatorLiveData<NetworkResult<NewsResponse>> = MediatorLiveData()

    private fun executeNewsFlow(country: String, category: String): Flow<NewsResponse> = flow {
        emit(newsApi.getNews(country, category, String(Constants.PACKS_NEWS)))
    }

    fun getNews(country: String, category: String): LiveData<NetworkResult<NewsResponse>> {
        viewModelScope.launch {
            try {
                executeNewsFlow(country, category)
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