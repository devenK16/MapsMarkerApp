package com.example.mapsmarkerapp.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.mapsmarkerapp.ui.components.MapsScreen1
import com.example.mapsmarkerapp.ui.theme.MapsMarkerAppTheme
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MapsMarkerAppTheme {
                // A surface container using the 'background' color from the theme
                MapsScreen1()
            }
        }
    }
}
