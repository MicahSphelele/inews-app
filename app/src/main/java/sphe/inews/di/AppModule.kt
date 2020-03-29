package sphe.inews.di

import dagger.Module
import dagger.Provides
import okhttp3.ConnectionSpec
import okhttp3.OkHttpClient
import okhttp3.TlsVersion
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sphe.inews.di.main.MainScope
import sphe.inews.ui.main.adapters.ArticleAdapter
import sphe.inews.util.Constants
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofitInstance(): Retrofit {
        val client = OkHttpClient.Builder()
        client.connectTimeout(35, TimeUnit.SECONDS)
        client.readTimeout(35, TimeUnit.SECONDS)
        client.writeTimeout(35, TimeUnit.SECONDS)

//        val specs: MutableList<ConnectionSpec> =
//            ArrayList()
//        specs.add(
//            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
//                .tlsVersions(TlsVersion.TLS_1_2)
//                .build()
//        )
//        specs.add(ConnectionSpec.COMPATIBLE_TLS)
//        specs.add(ConnectionSpec.CLEARTEXT)
//        client.connectionSpecs(specs)
//        client.hostnameVerifier { _, _ -> true }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Singleton
    @Provides
    @Named(Constants.BUSINESS)
    fun provideBusinessAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }

    @Singleton
    @Provides
    @Named(Constants.HEALTH)
    fun provideHealthAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }

    @Singleton
    @Provides
    @Named(Constants.SPORT)
    fun provideSportAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }

    @Singleton
    @Provides
    @Named(Constants.TECHNOLOGY)
    fun provideTechnologyAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }

    @Singleton
    @Provides
    @Named(Constants.ENTERTAINMENT)
    fun provideEntertainmentAdapter(): ArticleAdapter {
        return ArticleAdapter()
    }
}