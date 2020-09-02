package sphe.inews.di

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sphe.inews.di.main.MainFragmentBuildersModule
import sphe.inews.di.main.MainModule
import sphe.inews.di.main.MainScope
import sphe.inews.di.main.MainViewModelsModule
import sphe.inews.di.splash.SplashModule
import sphe.inews.di.splash.SplashScope
import sphe.inews.ui.SplashActivity
import sphe.inews.ui.main.MainActivity

@Module
abstract class ActivityBuildersModule {

    @MainScope
    @ContributesAndroidInjector(modules = [
        MainFragmentBuildersModule::class,
        MainViewModelsModule::class,
        MainModule::class])
    abstract fun contributeMainActivity(): MainActivity?

    @SplashScope
    @ContributesAndroidInjector(modules = [SplashModule::class])
    abstract fun contributeSplashActivity(): SplashActivity?

}