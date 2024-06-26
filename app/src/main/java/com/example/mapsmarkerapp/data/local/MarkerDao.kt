package com.example.mapsmarkerapp.data.local

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.mapsmarkerapp.data.models.MarkerEntity
import dagger.Provides
import kotlinx.coroutines.flow.Flow

@Dao
interface MarkerDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMarker(marker: MarkerEntity)

    @Update
    suspend fun updateMarker(marker: MarkerEntity)

    @Delete
    suspend fun deleteMarker(marker: MarkerEntity)

    @Query("SELECT * FROM markers")
    fun getAllMarkers(): Flow<List<MarkerEntity>>
}