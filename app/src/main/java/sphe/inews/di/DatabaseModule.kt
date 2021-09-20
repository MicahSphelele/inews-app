package sphe.inews.di

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import sphe.inews.data.local.dao.BookmarkDao
import sphe.inews.data.local.room.AppDB
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object DatabaseModule {

    @Singleton
    @Provides
    fun providesApplicationDatabase(@ApplicationContext context: Context) : AppDB = AppDB.getInstance(context)

    @Provides
    fun providesBookmarkDao(appDB: AppDB) : BookmarkDao = appDB.bookmarkDao()
}