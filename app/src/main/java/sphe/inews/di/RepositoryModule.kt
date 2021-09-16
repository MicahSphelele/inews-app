package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sphe.inews.domain.repository.BookmarkRepository
import sphe.inews.local.dao.BookmarkDao

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Provides
    fun provideBookmarkRepository(bookmarkDao: BookmarkDao) : BookmarkRepository =
        BookmarkRepository(bookmarkDao)
}