package com.medicify.app.di

import com.medicify.app.BuildConfig
import com.medicify.app.data.remote.ApiService
import com.medicify.app.data.repository.DrugsRepository
import com.medicify.app.data.repository.DrugsRepositoryImpl
import com.medicify.app.ui.MainViewModel
import com.medicify.app.ui.screen.camera.CameraViewModel
import com.medicify.app.ui.screen.detail.DetailViewModel
import com.medicify.app.ui.screen.home.HomeViewModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
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
            baseUrl(BuildConfig.API_BASE_URL)
            addConverterFactory(GsonConverterFactory.create())
            client(client)
        }.build()
        retrofit.create(ApiService::class.java)
    }

    single<DrugsRepository> {
        DrugsRepositoryImpl(get())
    }

    viewModel{
        HomeViewModel(get())
    }

    viewModel{
        CameraViewModel(get())
    }

    viewModel {
        MainViewModel()
    }

    viewModel{
        DetailViewModel(get())
    }
}