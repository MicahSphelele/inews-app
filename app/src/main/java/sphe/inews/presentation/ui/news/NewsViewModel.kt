package sphe.inews.presentation.ui.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import sphe.inews.domain.NetworkResult
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.domain.usecases.news.GetNewsByCategoryUseCase
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNewsByCategoryUseCase: GetNewsByCategoryUseCase) : ViewModel() {

    fun getNews(country: String, category: String) : LiveData<NetworkResult<NewsResponse>> {
        return getNewsByCategoryUseCase(viewModelScope, country, category)
    }
}