package com.example.mapsmarkerapp.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.text.input.KeyboardType
import com.example.mapsmarkerapp.data.models.MarkerData

@Composable
fun MarkerDetailsDialog(
    markerData: MarkerData?,
    onSave: (MarkerData) -> Unit,
    onDismiss: () -> Unit
) {
    var name by remember { mutableStateOf(markerData?.name ?: "") }
    var relation by remember { mutableStateOf(markerData?.relation ?: "") }
    var age by remember { mutableStateOf(markerData?.age ?: 0) }
    var address by remember { mutableStateOf(markerData?.address ?: "") }
    var latitude by remember { mutableStateOf(markerData?.latitude ?: 0.0) }
    var longitude by remember { mutableStateOf(markerData?.longitude ?: 0.0) }

    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(if (markerData == null) "Add Marker" else "Edit Marker") },
        text = {
            Column {
                OutlinedTextField(
                    value = name,
                    onValueChange = { name = it },
                    label = { Text("Name") }
                )
                OutlinedTextField(
                    value = relation,
                    onValueChange = { relation = it },
                    label = { Text("Relation") }
                )
                OutlinedTextField(
                    value = age.toString(),
                    onValueChange = { age = it.toIntOrNull() ?: 0 },
                    label = { Text("Age") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = address,
                    onValueChange = { address = it },
                    label = { Text("Address") },
                    enabled = false
                )
                OutlinedTextField(
                    value = latitude.toString(),
                    onValueChange = { latitude = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Latitude") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                OutlinedTextField(
                    value = longitude.toString(),
                    onValueChange = { longitude = it.toDoubleOrNull() ?: 0.0 },
                    label = { Text("Longitude") },
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        confirmButton = {
            Button(
                onClick = {
                    onSave(
                        MarkerData(
                            id = markerData?.id ?: 0,
                            name = name,
                            relation = relation,
                            age = age,
                            address = address,
                            latitude = latitude,
                            longitude = longitude
                        )
                    )
                }
            ) {
                Text("Save")
            }
        },
        dismissButton = {
            Button(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    )
}