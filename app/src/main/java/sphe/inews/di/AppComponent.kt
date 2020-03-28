package sphe.inews.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import sphe.inews.BaseApplication
import javax.inject.Singleton


@Singleton
@Component(modules = [
    AndroidSupportInjectionModule::class,
    AppModule::class,
    ActivityBuildersModule::class,
    ViewModelFactoryModule::class
])
interface AppComponent : AndroidInjector<BaseApplication> {


    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application):Builder
        fun build():AppComponent
    }

}