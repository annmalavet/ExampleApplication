package com.malavet.exampleapplication.network

import retrofit2.Retrofit
import javax.inject.Inject


class YourApiClient @Inject constructor(retrofit: Retrofit) {
    private val yourApiService: NetworkService = retrofit.create(NetworkService::class.java)

    // Define your API methods using the yourApiService instance
    // ...
}