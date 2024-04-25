package com.example.mapsmarkerapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.mapsmarkerapp.data.models.MarkerEntity

@Database(entities = [MarkerEntity::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun markerDao(): MarkerDao
}