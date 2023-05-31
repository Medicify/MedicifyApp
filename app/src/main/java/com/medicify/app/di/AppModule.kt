package com.medicify.app.di

import com.medicify.app.BuildConfig
import com.medicify.app.data.remote.ApiService
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.data.repository.DrugsRepositoryImpl
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val appModule = module {

    single {
        val loggingInterceptor = if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        } else {
            HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.NONE)
        }
        val client = OkHttpClient.Builder().apply {
            addInterceptor(loggingInterceptor)
        }.build()

        val retrofit = Retrofit.Builder().apply {
//            baseUrl(BuildConfig.API_BASE_URL)
            baseUrl("http://34.36.211.221/api/")
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
        retrofit.create(ApiService::class.java)
    }

    single<DrugsRepository> {
        DrugsRepositoryImpl(get())
    }
}