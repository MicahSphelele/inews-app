package sphe.inews.data.network

import retrofit2.http.GET
import retrofit2.http.Query
import sphe.inews.domain.models.news.NewsResponse
import sphe.inews.util.Constants

interface INewsApi {

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String = String(Constants.PACKS_NEWS)
    ): NewsResponse
}