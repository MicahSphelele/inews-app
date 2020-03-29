package sphe.inews.di.main

import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import sphe.inews.ui.main.business.BusinessFragment
import sphe.inews.ui.main.dialogs.AboutDialogFragment
import sphe.inews.ui.main.entertainment.EntertainmentFragment
import sphe.inews.ui.main.health.HealthFragment
import sphe.inews.ui.main.sport.SportFragment
import sphe.inews.ui.main.technology.TechnologyFragment

@Module
abstract class MainFragmentBuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeBusinessFragment(): BusinessFragment

    @ContributesAndroidInjector
    abstract fun contributesHealthFragment(): HealthFragment

    @ContributesAndroidInjector
    abstract fun contributesSportFragment(): SportFragment

    @ContributesAndroidInjector
    abstract fun contributesTechnologyFragment(): TechnologyFragment

    @ContributesAndroidInjector
    abstract fun contributesEntertainmentFragment(): EntertainmentFragment

    @ContributesAndroidInjector
    abstract fun contributesAboutFragmentDialog() : AboutDialogFragment

}