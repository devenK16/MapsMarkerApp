package com.example.mapsmarkerapp.ui.viewmodels

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


    fun saveMarker(markerData: MarkerData) {
        viewModelScope.launch {
            val updatedMarkerData = markerData.copy(isSaved = false )
            markerRepository.insertMarker(updatedMarkerData)

        }
    }

    fun updateMarker(markerData: MarkerData) {
        viewModelScope.launch {
            val updatedMarkerData = markerData.copy(isSaved = true )
            markerRepository.updateMarker(updatedMarkerData)

        }
    }

    fun deleteMarker(markerData: MarkerData) {
        viewModelScope.launch {
            markerRepository.deleteMarker(markerData)
        }
    }

}

data class MapsUiState(
    val markers: List<MarkerData> = emptyList()
)