package sphe.inews.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import sphe.inews.network.Covid19Api
import sphe.inews.network.INewsApi
import sphe.inews.network.WeatherApi
import sphe.inews.util.Constants
import javax.inject.Named

@InstallIn(SingletonComponent::class)
@Module
object NetworkModule {

    @Provides
    fun provideINewsApi(retrofit: Retrofit): INewsApi =
        retrofit.create(INewsApi::class.java)

    @Provides
    fun provideCovid19Api(@Named(Constants.NAMED_COVID_19) retrofit: Retrofit) : Covid19Api {
        return retrofit.create(Covid19Api::class.java)
    }

    @Provides
    fun provideWeatherApi(@Named(Constants.NAMED_WEATHER_API) retrofit: Retrofit) : WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }
}