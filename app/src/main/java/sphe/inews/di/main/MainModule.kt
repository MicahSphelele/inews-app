package sphe.inews.di.main

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import sphe.inews.network.INewsApi
import sphe.inews.ui.main.adapters.ArticleAdapter

@Module
class MainModule {


    @MainScope
    @Provides
    fun provideINewsApi(retrofit: Retrofit): INewsApi {
        return retrofit.create<INewsApi>(INewsApi::class.java)
    }

    @MainScope
    @Provides
    fun provideAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }
}