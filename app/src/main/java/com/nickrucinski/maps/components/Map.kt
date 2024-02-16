package com.nickrucinski.maps.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings

@Composable
fun CustomMapView(){
    var uiSettings by remember {
        mutableStateOf(MapUiSettings())
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.HYBRID))
    }

    Box() {
        GoogleMap(
            modifier = Modifier.fillMaxWidth(),
            properties = properties,
            uiSettings = uiSettings,
            cameraPositionState =  CameraPositionState(
                CameraPosition(
                    LatLng(22.5726, 88.3639), 12f, 0f, 0f
                )
            )
        ){
            uiSettings = uiSettings.copy(zoomControlsEnabled = false)

        }
        IconButton(
            onClick = {

            },
            modifier = Modifier
                .padding(bottom = 75.dp, end = 25.dp)
                .clip(CircleShape)
                .background(MaterialTheme.colorScheme.secondary)
                .align(Alignment.BottomEnd)
                .padding(10.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "add",
                tint = Color.Black,
                modifier = Modifier.size(40.dp)
            )
        }
    }
}