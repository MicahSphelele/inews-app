package sphe.inews.di.main

import android.app.Application
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import sphe.inews.local.repo.BookmarkRepository
import sphe.inews.models.Country
import sphe.inews.network.Covid19Api
import sphe.inews.network.INewsApi
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.ui.main.adapters.CountryAdapter
import sphe.inews.util.Constants
import javax.inject.Named

@Module
class MainModule {


    @MainScope
    @Provides
    fun provideINewsApi(retrofit: Retrofit): INewsApi {
        return retrofit.create(INewsApi::class.java)
    }

    @MainScope
    @Provides
    fun provideCovid19Api(@Named(Constants.NAMED_COVID_19) retrofit:Retrofit) : Covid19Api{
       return retrofit.create(Covid19Api::class.java)
    }

    @MainScope
    @Provides
    fun provideAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }

    @MainScope
    @Provides
    fun provideCountryAdapter(@Named(Constants.NAMED_COUNTRIES) list: List<Country>): CountryAdapter {
        return CountryAdapter(list)
    }

    @MainScope
    @Provides
    fun provideBookmarkRepository(application: Application) : BookmarkRepository {
        return BookmarkRepository(application)
    }


}