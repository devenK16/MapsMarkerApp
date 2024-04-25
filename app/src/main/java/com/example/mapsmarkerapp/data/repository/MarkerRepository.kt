package com.example.mapsmarkerapp.data.repository

import com.example.mapsmarkerapp.data.local.MarkerDao
import com.example.mapsmarkerapp.data.models.MarkerData
import com.example.mapsmarkerapp.data.models.MarkerEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MarkerRepository @Inject constructor(
    private val markerDao: MarkerDao
) {
    val allMarkers: Flow<List<MarkerData>> = markerDao.getAllMarkers().map { entities ->
        entities.map { entity ->
            entity.toMarkerData()
        }
    }

    suspend fun insertMarker(marker: MarkerData) {
        markerDao.insertMarker(marker.toMarkerEntity())
    }

    suspend fun updateMarker(marker: MarkerData) {
        markerDao.updateMarker(marker.toMarkerEntity())
    }

    suspend fun deleteMarker(marker: MarkerData) {
        markerDao.deleteMarker(marker.toMarkerEntity())
    }

    private fun MarkerEntity.toMarkerData(): MarkerData {
        return MarkerData(
            id = id,
            name = name,
            relation = relation,
            age = age,
            address = address,
            latitude = latitude,
            longitude = longitude,
            isSaved = true
        )
    }

    private fun MarkerData.toMarkerEntity(): MarkerEntity {
        return MarkerEntity(
            id = id,
            name = name,
            relation = relation,
            age = age,
            address = address,
            latitude = latitude,
            longitude = longitude
        )
    }
}