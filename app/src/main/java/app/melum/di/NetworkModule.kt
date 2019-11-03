package app.melum.di

import app.melum.BuildConfig
import app.melum.abstractions.Repository
import app.melum.data.connectivity.AndroidConnectedManager
import app.melum.data.connectivity.ConnectedManager
import app.melum.data.network.AddApiKeyInterceptor
import app.melum.data.network.LastFmApi
import app.melum.data.network.PrettyLogger
import app.melum.data.network.images.GoogleImageSearchApi
import app.melum.data.network.images.ImageSearch
import app.melum.data.network.images.MusicbrainzApi
import app.melum.data.repository.RepositoryImpl
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val CONNECTION_TIMEOUT = 30000L


const val LASTFM_API = "LASTFM_API"
const val GOOGLE_API = "GOOGLE_API"
const val MB_API = "MB_API"

const val HTTP_AUTH = "HTTP_API"
const val HTTP_UNAUTH = "HTTP_UNAUTH"



val networkModule = module {

    factory<Gson> { GsonBuilder().create() }

    factory(named(HTTP_AUTH)) {
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

    factory(named(HTTP_UNAUTH)) {
        OkHttpClient.Builder()
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

    factory<Converter.Factory> {
        GsonConverterFactory.create()
    }

    factory<Retrofit>(named(LASTFM_API)) {
        Retrofit.Builder().baseUrl(BuildConfig.API_BASE_URL)
            .addConverterFactory(get())
            .client(get(named(HTTP_AUTH)))
            .build()
    }
    factory<Retrofit>(named(GOOGLE_API)) {
        Retrofit.Builder().baseUrl("https://commons.wikimedia.org/")
            .addConverterFactory(get())
            .client(get(named(HTTP_UNAUTH)))
            .build()
    }

    factory<Retrofit>(named(MB_API)) {
        Retrofit.Builder().baseUrl("http://musicbrainz.org/")
            .addConverterFactory(get())
            .client(get(named(HTTP_UNAUTH)))
            .build()
    }

    factory<LastFmApi> { get<Retrofit>(named(LASTFM_API)).create(LastFmApi::class.java) }
    factory<GoogleImageSearchApi> { get<Retrofit>(named(GOOGLE_API)).create(GoogleImageSearchApi::class.java) }
    factory<MusicbrainzApi> { get<Retrofit>(named(MB_API)).create(MusicbrainzApi::class.java) }

    factory<Repository> { RepositoryImpl(get(), get()) }
    factory<ImageSearch> { ImageSearch(get(), get()) }

    single<ConnectedManager> { AndroidConnectedManager(androidContext()) }

}