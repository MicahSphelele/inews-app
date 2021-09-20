package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sphe.inews.domain.repository.BookmarkRepository
import sphe.inews.domain.repository.Covid19Repository
import sphe.inews.domain.repository.NewsRepository
import sphe.inews.domain.repository.WeatherRepository
import sphe.inews.domain.usecases.bookmark.*
import sphe.inews.domain.usecases.covid19.GetCovidStatsUseCase
import sphe.inews.domain.usecases.news.GetNewsByCategoryUseCase
import sphe.inews.domain.usecases.weather.GetCurrentWeatherUseCase
import sphe.inews.domain.usecases.weather.GetWeatherForecastUseCase

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun providesGetNewsByCategoryUseCase(newsRepository: NewsRepository): GetNewsByCategoryUseCase =
        GetNewsByCategoryUseCase(newsRepository)

    @Provides
    fun providesGetCovidStatsUseCase(covid19Repository: Covid19Repository): GetCovidStatsUseCase =
        GetCovidStatsUseCase(covid19Repository)

    @Provides
    fun providesGetCurrentWeatherUseCase(weatherRepository: WeatherRepository): GetCurrentWeatherUseCase =
        GetCurrentWeatherUseCase(weatherRepository)

    @Provides
    fun providesGetWeatherForecastUseCase(weatherRepository: WeatherRepository): GetWeatherForecastUseCase =
        GetWeatherForecastUseCase(weatherRepository)

    @Provides
    fun providesInsertBookmarkUseCase(bookmarkRepository: BookmarkRepository): InsertBookmarkUseCase =
        InsertBookmarkUseCase(bookmarkRepository)

    @Provides
    fun providesDeleteBookmarkUseCase(bookmarkRepository: BookmarkRepository): DeleteBookmarkUseCase =
        DeleteBookmarkUseCase(bookmarkRepository)

    @Provides
    fun providesGetBookmarkListUseCase(bookmarkRepository: BookmarkRepository): GetBookmarkListUseCase =
        GetBookmarkListUseCase(bookmarkRepository)

    @Provides
    fun providesGetBookmarksLiveDataUseCase(bookmarkRepository: BookmarkRepository): GetBookmarkLiveDataUseCase =
        GetBookmarkLiveDataUseCase(bookmarkRepository)

    @Provides
    fun providesGetBookmarkByUrlUseCase(bookmarkRepository: BookmarkRepository): GetSingleBookmarkByUrlUseCase =
        GetSingleBookmarkByUrlUseCase(bookmarkRepository)

    @Provides
    fun providesGetGetBookmarkByCatUseCase(bookmarkRepository: BookmarkRepository): GetBookmarkByCatUseCase =
        GetBookmarkByCatUseCase(bookmarkRepository)
}