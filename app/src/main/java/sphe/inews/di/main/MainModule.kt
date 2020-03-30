package sphe.inews.di.main

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import sphe.inews.network.Covid19Api
import sphe.inews.network.INewsApi
import sphe.inews.ui.main.adapters.ArticleAdapter
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
    fun provideCovid19Api(@Named("covid19") retrofit:Retrofit) : Covid19Api{
       return retrofit.create(Covid19Api::class.java)
    }

    @MainScope
    @Provides
    fun provideAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }

}