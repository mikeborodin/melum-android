package app.melum.di

import app.melum.BuildConfig
import app.melum.abstractions.Repository
import app.melum.data.connectivity.AndroidConnectedManager
import app.melum.data.connectivity.ConnectedManager
import app.melum.data.network.AddApiKeyInterceptor
import app.melum.data.network.LastFmApi
import app.melum.data.network.PrettyLogger
import app.melum.data.repository.RepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECTION_TIMEOUT = 30000L


val networkModule = module {

    factory<Gson> { GsonBuilder().create() }

    factory {
        OkHttpClient.Builder()
            .addInterceptor(AddApiKeyInterceptor())
            .connectTimeout(CONNECTION_TIMEOUT, TimeUnit.MILLISECONDS)
            .also {
                if (BuildConfig.DEBUG) {
                    it.addInterceptor(HttpLoggingInterceptor(PrettyLogger()).apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    })
                }
            }
            .build()
    }

    factory<Retrofit> {
        Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(get())
            .build()
    }

    factory<LastFmApi> { get<Retrofit>().create(LastFmApi::class.java) }

    factory<Repository> { RepositoryImpl(get()) }

    single<ConnectedManager> { AndroidConnectedManager(androidContext()) }

}