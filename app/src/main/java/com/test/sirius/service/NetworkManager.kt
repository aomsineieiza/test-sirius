package com.test.sirius.service

import com.google.gson.GsonBuilder
import com.test.sirius.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkManager {

    private fun getClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(getDefaultHttpLogging(BuildConfig.DEBUG))
            .build()
    }

    private fun getDefaultHttpLogging(isDebugMode: Boolean): HttpLoggingInterceptor {
        return if (isDebugMode) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
    }

    private fun getConverter(): Converter.Factory {
        return GsonConverterFactory.create(GsonBuilder().setPrettyPrinting().create())
    }

    private fun getCallAdapter(): RxJava2CallAdapterFactory {
        return RxJava2CallAdapterFactory.create()
    }

    fun <T> createService(serviceClass: Class<T>): T {
        return Retrofit.Builder()
            .baseUrl("https://raw.githubusercontent.com")
            .addConverterFactory(getConverter())
            .addCallAdapterFactory(getCallAdapter())
            .client(
                getClient()
            ).build()
            .create(serviceClass)
    }

    companion object {
        private const val TIMEOUT = 60L
    }
}