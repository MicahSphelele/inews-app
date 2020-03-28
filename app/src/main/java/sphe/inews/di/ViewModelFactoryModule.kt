package sphe.inews.di

import androidx.lifecycle.ViewModelProvider
import dagger.Binds
import dagger.Module
import sphe.inews.viewmodels.ViewModelProviderFactory

@Module
abstract class ViewModelFactoryModule {

    @Binds
    abstract fun bindViewModelFactory(viewModelFactory: ViewModelProviderFactory?): ViewModelProvider.Factory?
}