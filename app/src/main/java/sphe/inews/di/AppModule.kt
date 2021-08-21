package sphe.inews.di

import android.app.Application
import android.content.Context
import androidx.constraintlayout.widget.ConstraintSet
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sphe.inews.BaseApplication
import sphe.inews.R
import sphe.inews.enums.NewsCategory
import sphe.inews.models.Country
import sphe.inews.models.domain.ArticleBookmarkMapper
import sphe.inews.util.Constants
import sphe.inews.util.storage.AppStorage
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Singleton
    @Provides
    fun providesApplication(@ApplicationContext app: Context) : BaseApplication {
        return app as BaseApplication
    }

    @Singleton
    @Provides
    fun provideRetrofitNewsInstance(): Retrofit {
        val client = OkHttpClient.Builder()
        client.connectTimeout(35, TimeUnit.SECONDS)
        client.readTimeout(35, TimeUnit.SECONDS)
        client.writeTimeout(35, TimeUnit.SECONDS)

        val specs: MutableList<ConnectionSpec> = ArrayList()

        specs.add(
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build())
        specs.add(ConnectionSpec.COMPATIBLE_TLS)
        specs.add(ConnectionSpec.CLEARTEXT)
        client.connectionSpecs(specs)
        client.hostnameVerifier { _, _ -> true }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_NEWS_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_COVID_19)
    fun provideRetrofitCovid19Instance(): Retrofit {
        val client = OkHttpClient.Builder()
        client.connectTimeout(35, TimeUnit.SECONDS)
        client.readTimeout(35, TimeUnit.SECONDS)
        client.writeTimeout(35, TimeUnit.SECONDS)

        val specs: MutableList<ConnectionSpec> = ArrayList()

        specs.add(
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build())
        specs.add(ConnectionSpec.COMPATIBLE_TLS)
        specs.add(ConnectionSpec.CLEARTEXT)
        client.connectionSpecs(specs)
        client.hostnameVerifier { _, _ -> true }

        client.addInterceptor(Interceptor { chain ->
            val original: Request = chain.request()
            val request = original.newBuilder()
                .header("x-rapidapi-host", "coronavirus-monitor.p.rapidapi.com")
                .header("x-rapidapi-key", String(Constants.PACKS_COVID))
                .method(original.method(), original.body())
                .build()

            return@Interceptor chain.proceed(request)
        })

        return Retrofit.Builder()
            .baseUrl(Constants.BASE_COVID_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofitWeatherInstance(): Retrofit {
        val client = OkHttpClient.Builder()
        client.connectTimeout(35, TimeUnit.SECONDS)
        client.readTimeout(35, TimeUnit.SECONDS)
        client.writeTimeout(35, TimeUnit.SECONDS)

        val specs: MutableList<ConnectionSpec> = ArrayList()

        specs.add(
            ConnectionSpec.Builder(ConnectionSpec.MODERN_TLS)
                .tlsVersions(TlsVersion.TLS_1_2)
                .build())
        specs.add(ConnectionSpec.COMPATIBLE_TLS)
        specs.add(ConnectionSpec.CLEARTEXT)
        client.connectionSpecs(specs)
        client.hostnameVerifier { _, _ -> true }
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_WEATHER_API_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .client(client.build())
            .build()
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_APP_VERSION)
    fun provideAppVersion(application: Application): String{

        return application.packageManager.getPackageInfo(application.packageName,0).versionName
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_SET_OLD)
    fun provideConstraintSet1() : ConstraintSet {
        return ConstraintSet()
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_SET_NEW)
    fun provideConstraintSet2() : ConstraintSet {
        return ConstraintSet()
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_COUNTRIES)
    fun provideCountryData() : List<Country>{

        return listOf(
            Country("South Africa","za", R.drawable.flag_south_africa),
            Country("UAE","ua", R.drawable.flag_uae),
            Country("Italy","it", R.drawable.flag_italy),
            Country("Egypt","eg", R.drawable.flag_egypt),
            Country("China","ch", R.drawable.flag_china),
            Country("USA","us", R.drawable.flag__usa),
            Country("UK","uk", R.drawable.flag__uk),
            Country("Morocco","ma", R.drawable.flag__morocco),
            Country("Japan","jp", R.drawable.flag__japan)

        )
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_CATEGORIES)
    fun provideFilterData() : Array<NewsCategory>{
        return NewsCategory.values()
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_STORAGE)
    fun provideAppStorage(application: Application) : AppStorage {
        return AppStorage(application)
    }

    @Singleton
    @Provides
    @Named(Constants.NAMED_IS_ON_TEST_MODE)
    fun providesIsOnTestMode() : Boolean {
        return false
    }

    @Singleton
    @Provides
    fun providesRecipeMapper() : ArticleBookmarkMapper {
        return ArticleBookmarkMapper()
    }
}