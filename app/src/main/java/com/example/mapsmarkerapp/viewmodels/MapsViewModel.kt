package com.example.mapsmarkerapp.viewmodels

import android.location.Geocoder
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.mapsmarkerapp.data.models.MarkerData
import com.example.mapsmarkerapp.data.repository.MarkerRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val markerRepository: MarkerRepository,
    private val geocoder: Geocoder
) : ViewModel() {

    private val _uiState = MutableStateFlow(MapsUiState())
    val uiState: StateFlow<MapsUiState> = _uiState

    init {
        viewModelScope.launch {
            markerRepository.allMarkers.collect { markers ->
                _uiState.value = _uiState.value.copy(markers = markers)
            }
        }
    }

    fun saveOrUpdateMarker(markerData: MarkerData) {
        viewModelScope.launch {
            val address = getAddressFromCoordinates(markerData.latitude, markerData.longitude)
            val updatedMarkerData = markerData.copy(address = address, isSaved = true)

            if (updatedMarkerData.id == 0L) {
                markerRepository.insertMarker(updatedMarkerData)
            } else {
                markerRepository.updateMarker(updatedMarkerData)
            }
        }
    }

    fun deleteMarker(markerData: MarkerData) {
        viewModelScope.launch {
            markerRepository.deleteMarker(markerData)
        }
    }

    private fun getAddressFromCoordinates(latitude: Double, longitude: Double): String {
        val addresses = geocoder.getFromLocation(latitude, longitude, 1)
        return if (addresses != null && addresses.isNotEmpty()) {
            addresses[0].getAddressLine(0)
        } else {
            "Unknown Address"
        }
    }
}

data class MapsUiState(
    val markers: List<MarkerData> = emptyList()
)