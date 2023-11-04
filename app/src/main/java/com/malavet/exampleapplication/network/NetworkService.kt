package com.malavet.exampleapplication.network

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class BaseUrl


@Module
@InstallIn(SingletonComponent::class)
object NetworkService  {

    val url = "https://malavetdata.s3.amazonaws.com/"

    val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(loggingInterceptor)
        .build()

     val retrofit: Retrofit = Retrofit.Builder()
         .baseUrl(url)
         .client(client)
         .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
         .build()

    var profileApi: Endpoints = retrofit.create(Endpoints::class.java)
}

