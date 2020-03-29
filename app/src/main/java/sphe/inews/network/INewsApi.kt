package sphe.inews.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import sphe.inews.models.NewsResponse

interface INewsApi {

    @GET("top-headlines?category=business")
    fun getBusinessNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=health")
    fun getHealthNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=sports")
    fun getSportNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=technology")
    fun getTechNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=entertainment")
    fun getEntertainmentNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

}