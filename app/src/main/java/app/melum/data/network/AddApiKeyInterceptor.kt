package app.melum.data.network

import app.melum.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response


class AddApiKeyInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url.newBuilder()
            .addQueryParameter("api_key", BuildConfig.API_KEY)
            .addQueryParameter("format", "json")
            .build()

        return chain.proceed(
            chain.request().newBuilder()
                .url(url)
                .addHeader("Content-type", "application")
                .build()
        )
    }
}