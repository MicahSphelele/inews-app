package sphe.inews.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import sphe.inews.models.NewsResponse

interface INewsApi {

    @GET("top-headlines?category=business")//business
    fun getBusinessNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=health")//health
    fun getHealthNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=sports")//sports
    fun getSportNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=technology")//technology
    fun getTechNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

    @GET("top-headlines?category=entertainment")//entertainment
    fun getEntertainmentNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?

}