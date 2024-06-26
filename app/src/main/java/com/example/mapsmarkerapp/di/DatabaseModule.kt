package com.example.mapsmarkerapp.di

import android.content.Context
import androidx.room.Room
import com.example.mapsmarkerapp.data.local.AppDatabase
import com.example.mapsmarkerapp.data.local.MarkerDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext appContext: Context): AppDatabase {
        return Room.databaseBuilder(
            appContext,
            AppDatabase::class.java,
            "marker_database"
        ).build()
    }
    @Provides
    @Singleton
    fun provideMarkerDao(appDatabase: AppDatabase): MarkerDao {
        return appDatabase.markerDao()
    }

}