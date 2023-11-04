package com.malavet.exampleapplication.network

import com.malavet.exampleapplication.data.models.LayoutOrder
import com.malavet.exampleapplication.data.models.Users
import retrofit2.http.GET

interface Endpoints {

        @GET("config.json")
        suspend fun getDataTest(): LayoutOrder

        @GET("data.json")
        suspend fun getAll():Users
}
