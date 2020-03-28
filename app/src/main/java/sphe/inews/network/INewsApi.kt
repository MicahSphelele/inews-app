package sphe.inews.network

import io.reactivex.Flowable
import retrofit2.http.GET
import retrofit2.http.Query
import sphe.inews.models.NewsResponse

interface INewsApi {


    //http://newsapi.org/v2/
    // top-headlines
    // ?country=za&category=health&apiKey=4e3afc02ff264ee49676fb2240c398c8
    @GET("top-headlines?category=business")
    fun getBusinessNews(@Query("country")country: String, @Query("apiKey") apiKey :String): Flowable<NewsResponse>?
}