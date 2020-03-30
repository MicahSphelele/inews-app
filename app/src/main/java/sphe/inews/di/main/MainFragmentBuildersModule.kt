package sphe.inews.di.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sphe.inews.ui.main.news.business.BusinessFragment
import sphe.inews.ui.main.news.entertainment.EntertainmentFragment
import sphe.inews.ui.main.news.health.HealthFragment
import sphe.inews.ui.main.news.sport.SportFragment
import sphe.inews.ui.main.news.technology.TechnologyFragment
import sphe.inews.ui.main.dialogfragments.AboutDialogFragment

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