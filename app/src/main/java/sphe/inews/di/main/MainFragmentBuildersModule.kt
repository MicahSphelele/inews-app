package sphe.inews.di.main

import dagger.Module
import dagger.android.ContributesAndroidInjector
import sphe.inews.ui.bottomsheet.DaggerBottomSheetDialogFragment
import sphe.inews.ui.main.news.business.BusinessFragment
import sphe.inews.ui.main.news.entertainment.EntertainmentFragment
import sphe.inews.ui.main.news.health.HealthFragment
import sphe.inews.ui.main.news.sport.SportFragment
import sphe.inews.ui.main.news.technology.TechnologyFragment
import sphe.inews.ui.main.dialogfragments.AboutDialogFragment
import sphe.inews.ui.main.dialogfragments.ArticlePreviewDialogFragment
import sphe.inews.ui.main.dialogfragments.ViewYoutubeDialogFragment
import sphe.inews.ui.main.dialogfragments.covid.CovidStatDialogFragment

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
    abstract fun contributesAboutDialogFragment() : AboutDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesCovidStatDialogFragment() : CovidStatDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesViewYoutubeDialogFragment() : ViewYoutubeDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesArticlePreviewDialogFragment() : ArticlePreviewDialogFragment

    @ContributesAndroidInjector
    abstract fun contributesDaggerBottomSheetDialogFragment() : DaggerBottomSheetDialogFragment

}