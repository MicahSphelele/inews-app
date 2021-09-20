package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sphe.inews.domain.repository.BookmarkRepository
import sphe.inews.data.repository.RealBookmarkRepository
import sphe.inews.data.local.dao.BookmarkDao
import sphe.inews.data.network.Covid19Api
import sphe.inews.data.network.INewsApi
import sphe.inews.data.network.WeatherApi
import sphe.inews.data.repository.RealCovid19Repository
import sphe.inews.data.repository.RealNewsRepository
import sphe.inews.data.repository.RealWeatherRepository
import sphe.inews.domain.repository.Covid19Repository
import sphe.inews.domain.repository.NewsRepository
import sphe.inews.domain.repository.WeatherRepository

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao) : BookmarkRepository =
        RealBookmarkRepository(bookmarkDao)

    @Provides
    fun provideNewsRepository(newsApi: INewsApi) : NewsRepository =
        RealNewsRepository(newsApi)

    @Provides
    fun provideCovid19Repository(covid19Api: Covid19Api) : Covid19Repository =
        RealCovid19Repository(covid19Api)

    @Provides
    fun provideWeatherRepository(weatherApi: WeatherApi) : WeatherRepository =
        RealWeatherRepository(weatherApi)
}