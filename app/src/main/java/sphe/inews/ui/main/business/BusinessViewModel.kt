package sphe.inews.ui.main.business

import android.util.Log
import androidx.lifecycle.*
import io.reactivex.functions.Function
import io.reactivex.schedulers.Schedulers
import sphe.inews.models.NewsResponse
import sphe.inews.network.INewResource
import sphe.inews.network.INewsApi
import sphe.inews.util.Constants
import javax.inject.Inject

class BusinessViewModel @Inject constructor(private var api:INewsApi) : ViewModel() {

    private var newsResponse: MediatorLiveData<INewResource<NewsResponse>>? = null

    fun observeBusinessNews(): LiveData<INewResource<NewsResponse>>? {

        newsResponse = MediatorLiveData<INewResource<NewsResponse>>()
        newsResponse?.value =  INewResource.loading(NewsResponse(null,"loading",0))

        val source: LiveData<INewResource<NewsResponse>?> =
            LiveDataReactiveStreams.fromPublisher(
                api.getBusinessNews("za", String(Constants.PACKS))
                    ?.onErrorReturn { throwable ->
                        throwable.stackTrace
                        Log.e("@BusinessViewModel","apply error: $throwable")

                        return@onErrorReturn NewsResponse(null,"error",0)
                    }
                    ?.map(object :
                        Function<NewsResponse, INewResource<NewsResponse>?> {
                        @Throws(Exception::class)
                        override fun apply(response: NewsResponse): INewResource<NewsResponse>? {
                            Log.d("@BusinessViewModel","apply data")
                            if (response.totalResults!! == 0) {
                                    return INewResource.error("Something went wrong", null)
                            }
                            return INewResource.success(response)
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