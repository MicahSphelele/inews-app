package sphe.inews.ui.main.technology

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import sphe.inews.models.news.NewsResponse
import sphe.inews.network.Resources
import sphe.inews.network.INewsApi
import sphe.inews.util.Constants
import javax.inject.Inject

class TechnologyViewModel @Inject constructor(private var api: INewsApi) : ViewModel() {

    private var newsResponse: MediatorLiveData<Resources<NewsResponse>>? = null

    fun observeTechnologyNews(country:String): LiveData<Resources<NewsResponse>>? {

        newsResponse = MediatorLiveData<Resources<NewsResponse>>()
        newsResponse?.value =  Resources.loading(
            NewsResponse(
                null,
                "loading",
                0
            )
        )

        val source: LiveData<Resources<NewsResponse>?> =
            LiveDataReactiveStreams.fromPublisher(
                api.getTechNews(country, String(Constants.PACKS))
                    ?.onErrorReturn { throwable ->
                        throwable.stackTrace
                        Log.e("@TechnologyViewModel","apply error: $throwable")

                        return@onErrorReturn NewsResponse(
                            null,
                            "error",
                            0
                        )
                    }
                    ?.map(object :
                        Function<NewsResponse, Resources<NewsResponse>?> {
                        @Throws(Exception::class)
                        override fun apply(response: NewsResponse): Resources<NewsResponse>? {
                            Log.d("@BusinessViewModel","apply data")
                            if (response.totalResults!! == 0) {
                                return Resources.error("Something went wrong", null)
                            }
                            return Resources.success(response)
                        }
                    })!!.subscribeOn(Schedulers.io())
            )

        newsResponse?.addSource(source) { response ->
            newsResponse.apply {
                this?.value = response
                this?.removeSource(source)
            }
        }

        return newsResponse
    }
}