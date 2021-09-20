package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sphe.inews.domain.repository.BookmarkRepository
import sphe.inews.domain.repository.NewsRepository
import sphe.inews.domain.usecases.bookmark.*
import sphe.inews.domain.usecases.news.GetNewsByCategoryUseCase

@InstallIn(SingletonComponent::class)
@Module
object UseCaseModule {

    @Provides
    fun providesGetNewsByCategoryUseCase(newsRepository: NewsRepository) : GetNewsByCategoryUseCase =
        GetNewsByCategoryUseCase(newsRepository)

    @Provides
    fun providesInsertBookmarkUseCase(bookmarkRepository: BookmarkRepository) : InsertBookmarkUseCase =
        InsertBookmarkUseCase(bookmarkRepository)

    @Provides
    fun providesDeleteBookmarkUseCase(bookmarkRepository: BookmarkRepository) : DeleteBookmarkUseCase =
        DeleteBookmarkUseCase(bookmarkRepository)

    @Provides
    fun providesGetBookmarkListUseCase(bookmarkRepository: BookmarkRepository) : GetBookmarkListUseCase =
        GetBookmarkListUseCase(bookmarkRepository)

    @Provides
    fun providesGetBookmarksLiveDataUseCase(bookmarkRepository: BookmarkRepository) : GetBookmarkLiveDataUseCase =
        GetBookmarkLiveDataUseCase(bookmarkRepository)

    @Provides
    fun providesGetBookmarkByUrlUseCase(bookmarkRepository: BookmarkRepository) : GetSingleBookmarkByUrlUseCase =
        GetSingleBookmarkByUrlUseCase(bookmarkRepository)

    @Provides
    fun providesGetGetBookmarkByCatUseCase(bookmarkRepository: BookmarkRepository) : GetBookmarkByCatUseCase =
        GetBookmarkByCatUseCase(bookmarkRepository)
}