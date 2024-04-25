import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import com.example.mapsmarkerapp.data.models.MarkerData
import com.example.mapsmarkerapp.viewmodels.MapsViewModel
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.rememberCameraPositionState

@Composable
fun MapsView(
    modifier: Modifier = Modifier,
    viewModel: MapsViewModel,
    onMarkerClick: (MarkerData?) -> Unit,
    onMarkerLongClick: (MarkerData) -> Unit
) {
    val context = LocalContext.current
    GoogleMap(
        modifier = modifier,
        cameraPositionState = rememberCameraPositionState {
            position = CameraPosition.fromLatLngZoom(LatLng(37.4219983, -122.0840572), 10f)
        },
        onMapLoaded = { mapView ->
            mapView.getMapAsync { googleMap ->
                googleMap.setOnMarkerClickListener { marker ->
                    val markerData = marker.tag as? MarkerData
                    onMarkerClick(markerData)
                    true
                }

                googleMap.setOnMarkerLongClickListener { marker ->
                    val markerData = marker.tag as? MarkerData ?: return@setOnMarkerLongClickListener false
                    onMarkerLongClick(markerData)
                    true
                }

                viewModel.uiState.collectAsState().value.markers.forEach { markerData ->
                    val markerOptions = MarkerOptions()
                        .position(LatLng(markerData.latitude, markerData.longitude))
                        .title(markerData.name)
                        .snippet(markerData.address)
                        .icon(if (markerData.isSaved) BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE) else BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED))

                    val marker = googleMap.addMarker(markerOptions)
                    marker?.tag = markerData
                }
            }
        }
    )
}