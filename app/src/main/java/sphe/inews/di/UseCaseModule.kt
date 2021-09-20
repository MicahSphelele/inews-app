package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sphe.inews.domain.repository.NewsRepository
import sphe.inews.domain.usecases.news.GetNewsByCategoryUseCase

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun providesGetNewsByCategoryUseCase(newsRepository: NewsRepository) : GetNewsByCategoryUseCase =
        GetNewsByCategoryUseCase(newsRepository)
}