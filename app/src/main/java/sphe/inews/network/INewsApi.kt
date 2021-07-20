package sphe.inews.network

import io.reactivex.Flowable
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import sphe.inews.models.news.NewsResponse
import sphe.inews.util.Constants

interface INewsApi {

    @GET("top-headlines?category=${Constants.BUSINESS}")//business
    fun getBusinessNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Flowable<NewsResponse>?

    @GET("top-headlines?category=${Constants.HEALTH}")//health
    fun getHealthNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Flowable<NewsResponse>?

    @GET("top-headlines?category=${Constants.SPORTS}")//sports
    fun getSportNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Flowable<NewsResponse>?

    @GET("top-headlines?category=${Constants.TECHNOLOGY}")//technology
    fun getTechNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Flowable<NewsResponse>?

    @GET("top-headlines?category=${Constants.ENTERTAINMENT}")//entertainment
    fun getEntertainmentNews(
        @Query("country") country: String,
        @Query("apiKey") apiKey: String
    ): Flowable<NewsResponse>?

    @GET("top-headlines")
    suspend fun getNews(
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("apiKey") apiKey: String
    ) : NewsResponse
}