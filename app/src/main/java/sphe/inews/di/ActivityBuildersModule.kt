package sphe.inews.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sphe.inews.di.main.MainFragmentBuildersModule
import sphe.inews.di.main.MainModule
import sphe.inews.di.main.MainScope
import sphe.inews.di.main.MainViewModelsModule
import sphe.inews.ui.main.MainActivity

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = [
        MainFragmentBuildersModule::class,
        MainViewModelsModule::class,
        MainModule::class])
    abstract fun contributeMainActivity(): MainActivity?
}