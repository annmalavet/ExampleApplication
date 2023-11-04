package com.malavet.exampleapplication.di

import android.content.Context
import androidx.room.Room
import com.malavet.data.local.database.ProfileDatabase
import com.malavet.exampleapplication.data.ProfilesRepository
import com.malavet.exampleapplication.data.database.DataDao
import com.malavet.exampleapplication.data.models.LayoutOrder
import com.malavet.exampleapplication.data.models.Profile
import com.malavet.exampleapplication.network.Endpoints
import com.malavet.exampleapplication.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton


/*
* All dependency injection in this file, as app grows should be localized to respecitve layers
* */
@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideProfileDatabase(@ApplicationContext context: Context): ProfileDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            ProfileDatabase::class.java,
            "Profile.db"
        ).build()
    }


    val url = "https://malavetdata.s3.amazonaws.com/"

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create()) // Use Gson as the converter
            .build()
    }

    @Provides
    @Singleton
    fun provideApiService(retrofit: Retrofit): Endpoints {
        return retrofit.create(Endpoints::class.java)
    }

    @Provides
    fun provideNetworkService(): NetworkService {
        return NetworkService
    }

    @Provides
    fun provideProfile(): Profile {
        return Profile(1, "Name", "m", listOf("4"), "", "", "")
    }

    @Provides
    fun provideLayoutOrder(): LayoutOrder {
        return LayoutOrder(listOf("name", "gender"))
    }

    @Provides
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(Dispatchers.IO)
    }

    @Provides
    fun provideProfileRepository(
        apiService: NetworkService,
        dataDao: DataDao,
    ): ProfilesRepository {
        return ProfilesRepository(
            apiService,
            dataDao
        )
    }

    @Provides
    fun provideDataDao(database: ProfileDatabase): DataDao {
        return database.dataDao()
    }

}
