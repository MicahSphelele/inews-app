package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import sphe.inews.domain.enums.NewsCategory
import sphe.inews.domain.models.Country
import sphe.inews.presentation.main.adapters.ArticleAdapter
import sphe.inews.presentation.main.adapters.CategoryAdapter
import sphe.inews.presentation.main.adapters.CountryAdapter
import sphe.inews.util.Constants
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object MainModule {

    @Provides
    fun provideAdapter(): ArticleAdapter =
        ArticleAdapter()

    @Provides
    fun provideCountryAdapter(@Named(Constants.NAMED_COUNTRIES) list: List<Country>): CountryAdapter =
        CountryAdapter(list)

    @Provides
    fun provideCategoryAdapter(@Named(Constants.NAMED_CATEGORIES) list: Array<NewsCategory>): CategoryAdapter =
    CategoryAdapter(list)

}