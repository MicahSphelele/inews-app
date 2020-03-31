package sphe.inews.di

import dagger.Module
import dagger.Provides
import okhttp3.*
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import sphe.inews.util.Constants
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class AppModule {

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
    @Named("covid19")
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

}