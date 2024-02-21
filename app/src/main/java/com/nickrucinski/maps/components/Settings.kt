package com.nickrucinski.maps.components

import android.content.Context
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import com.nickrucinski.maps.Utils

@Composable
fun SettingsScreen(
    context: Context
){
    val params = HashMap<String, String>()
    params["Title"] = "23"
    params["Director"] = "56"
    var temperatureText by remember { mutableStateOf("32") }
    var humidityText by remember { mutableStateOf("50") }

    Column {
        TextField(
            value = temperatureText,
            onValueChange = { temperatureText = it },
            label = { Text("Temperature") }
        )

        TextField(
            value = humidityText,
            onValueChange = { humidityText = it },
            label = { Text("Humidity") }
        )
        Button(
            onClick = {
                Utils().getDataFromAPI(
                    "http://10.0.2.2:8000/add-film/",
                    context = context,
                    params,
                    {
                    }
                )
            }
        ) {

        }

    }

}