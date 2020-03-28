package sphe.inews

import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication
import sphe.inews.di.DaggerAppComponent


class BaseApplication : DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
        //A simple news app demo using Dagger 2 and MVVM
    }
}