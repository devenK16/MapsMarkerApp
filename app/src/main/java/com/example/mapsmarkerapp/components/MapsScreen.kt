package com.example.mapsmarkerapp.components

import MapsView
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mapsmarkerapp.data.models.MarkerData
import com.example.mapsmarkerapp.viewmodels.MapsViewModel
import com.google.android.gms.maps.GoogleMap

@Composable
fun MapsScreen(
    viewModel: MapsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val markerDetailsDialog = remember { mutableStateOf<MarkerData?>(null) }
    val markerToDelete = remember { mutableStateOf<MarkerData?>(null) }
    MapsView(
        modifier = Modifier.fillMaxSize(),
        viewModel = viewModel,
        onMarkerClick = { markerData ->
            markerDetailsDialog.value = markerData
        },
        onMarkerLongClick = { markerData ->
            markerToDelete.value = markerData
        }
    )
    

    if (markerDetailsDialog.value != null) {
        MarkerDetailsDialog(
            markerData = markerDetailsDialog.value,
            onSave = { updatedMarkerData ->
                viewModel.saveOrUpdateMarker(updatedMarkerData)
                markerDetailsDialog.value = null
            },
            onDismiss = { markerDetailsDialog.value = null }
        )
    }

    if (markerToDelete.value != null) {
        DeleteMarkerConfirmationDialog(
            markerData = markerToDelete.value,
            onConfirm = {
                viewModel.deleteMarker(markerToDelete.value!!)
                markerToDelete.value = null
            },
            onDismiss = { markerToDelete.value = null }
        )
    }
}