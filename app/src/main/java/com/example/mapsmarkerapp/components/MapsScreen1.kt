package com.example.mapsmarkerapp.components

import android.Manifest
import android.location.Geocoder
import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.mapsmarkerapp.data.models.MarkerData
import com.example.mapsmarkerapp.viewmodels.MapsViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.google.maps.android.compose.rememberCameraPositionState

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun MapsScreen1(
    viewModel: MapsViewModel = hiltViewModel(),
    geocoder: Geocoder = Geocoder(LocalContext.current)
) {
    val uiState by viewModel.uiState.collectAsState()

    val permissionsState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )
    val mapProperties by remember {
        mutableStateOf(
            MapProperties(
                isMyLocationEnabled = permissionsState.allPermissionsGranted
            )
        )
    }
    val cameraPositionState = rememberCameraPositionState()
    val selectedMarker = remember { mutableStateOf<MarkerData?>(null) }
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
            properties = mapProperties,
            onMapClick = { latLng ->
                viewModel.saveMarker(
                    MarkerData(
                        name = "",
                        relation = "",
                        age = 0,
                        address = getAddressFromCoordinates(
                            latLng.latitude,
                            latLng.longitude,
                            geocoder
                        ),
                        latitude = latLng.latitude,
                        longitude = latLng.longitude,
                        isSaved = false
                    )
                )
            }
        ) {
            uiState.markers.forEach { marker ->
                Marker(
                    state = MarkerState(position = LatLng(marker.latitude, marker.longitude)),
                    title = marker.name,
                    snippet = marker.address,
                    icon = if (marker.isSaved == true) {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) // Change marker color to blue for selected marker
                    } else {
                        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED) // Change marker color to red for other markers
                    },
                    onClick = {
                        selectedMarker.value = marker
                        true
                    }
                )

            }
        }

    }

    selectedMarker.value?.let { marker ->
        showUpdateMarkerDialog(marker, selectedMarker, viewModel) { /* Handle dismiss */ }
    }

}

@Composable
private fun showUpdateMarkerDialog(
    marker: MarkerData,
    selectedMarker: MutableState<MarkerData?>,
    viewModel: MapsViewModel,
    onDismissRequest: () -> Unit
) {

    val context = LocalContext.current

    // Get the activity from the context
    val activity = LocalContext.current as? AppCompatActivity
    activity?.let {
        // Add callback to the back press dispatcher of the activity
        it.onBackPressedDispatcher.addCallback(object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                // Handle back press event, e.g., dismiss the dialog
                selectedMarker.value = null
                onDismissRequest() // Call the onDismissRequest function here
            }
        })
    }


    var name by remember { mutableStateOf(marker.name) }
    var relation by remember { mutableStateOf(marker.relation) }
    var age by remember { mutableStateOf(marker.age) }
    var address by remember { mutableStateOf(marker.address) }
    var latitude by remember { mutableStateOf(marker.latitude) }
    var longitude by remember { mutableStateOf(marker.longitude) }


    AlertDialog(
        modifier = Modifier.padding(10.dp),
        onDismissRequest = onDismissRequest,
        title = { Text(text = "Enter Personal Detail") },
        text = {
            Column {
                OutlinedTextField(

                    value = name,
                    onValueChange = { name = it },
                    label = { Text(text = "Name") }
                )
                OutlinedTextField(
                    value = relation,
                    onValueChange = { relation = it },
                    label = { Text(text = "Relation") }
                )
                OutlinedTextField(

                    value = age.toString(),
                    onValueChange = { age = it.toIntOrNull() ?: 0 },
                    label = { Text(text = "Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text(text = "Address") }
                )
                OutlinedTextField(

                    value = latitude.toString(),
                    onValueChange = { latitude = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text(text = "Latitude") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = longitude.toString(),
                    onValueChange = { longitude = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text(text = "Longitude") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    viewModel.updateMarker(
                        marker.copy(
                            name = name,
                            relation = relation,
                            age = age,
                            address = address,
                            latitude = latitude,
                            longitude = longitude,
                            isSaved = true
                        )
                    )
                    selectedMarker.value = null
                    onDismissRequest()
                }
            ) {
                Text(text = "Update", color = Color.Black)
            }
        },
        dismissButton = {
            Button(
                onClick = {
                    viewModel.deleteMarker(marker)
                    selectedMarker.value = null
                    onDismissRequest()
                }
            ) {
                Text(text = "Delete", color = Color.Black)
            }
        }
    )
}

private fun getAddressFromCoordinates(
    latitude: Double,
    longitude: Double,
    geocoder: Geocoder
): String {
    val addresses = geocoder.getFromLocation(latitude, longitude, 1)
    return if (addresses != null && addresses.isNotEmpty()) {
        addresses[0].getAddressLine(0)
    } else {
        "Unknown Address"
    }
}