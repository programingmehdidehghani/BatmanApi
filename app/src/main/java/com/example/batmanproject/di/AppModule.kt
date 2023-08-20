package com.example.batmanproject.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.batmanproject.api.ApiService
import com.example.batmanproject.db.BatmanDB
import com.example.batmanproject.util.Constant.Companion.BASE_URL
import com.example.batmanproject.util.Constant.Companion.DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun retrofit() : ApiService {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create()
    }

    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        BatmanDB::class.java,
        DATABASE_NAME
    ).build()

}