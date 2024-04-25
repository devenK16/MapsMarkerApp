package com.example.mapsmarkerapp.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "markers")
data class MarkerEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val name: String,
    val relation: String,
    val age: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double
)
