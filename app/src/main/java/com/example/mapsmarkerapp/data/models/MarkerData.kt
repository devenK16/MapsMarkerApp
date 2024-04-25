package com.example.mapsmarkerapp.data.models

data class MarkerData(
    val id: Long = 0,
    val name: String,
    val relation: String,
    val age: Int,
    val address: String,
    val latitude: Double,
    val longitude: Double,
    val isSaved: Boolean = false
)
