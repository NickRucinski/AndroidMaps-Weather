package com.nickrucinski.maps.components

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.LatLng
import com.google.gson.Gson
import com.nickrucinski.maps.R
import com.nickrucinski.maps.Utils
import com.nickrucinski.maps.WEATHER_URL
import com.nickrucinski.maps.WeatherData


@Composable
fun WeatherScreen(){
    val iconMap = Utils().createIconMap()
    val userLocation = LatLng(40.28640022545426,-75.26387422816678)
    val tempJSON = Utils().ReadJSONFromAssets(LocalContext.current, "response.json")
    var weatherData: WeatherData = Gson().fromJson(tempJSON, WeatherData::class.java)
    Utils().getDataFromWeatherAPI(
        "$WEATHER_URL${userLocation.latitude},${userLocation.longitude}",
        LocalContext.current,
        )
        {
            Log.d("api", it.toString())
            weatherData = Gson().fromJson(it.toString(), WeatherData::class.java)
        }

    Column {
        CurrentWeatherDisplay()
        WeatherBar(weatherData = weatherData, iconMap = iconMap)
        WeatherTable(weatherData = weatherData.daily.data, iconMap = iconMap)
    }
}
@Composable
fun WeatherBar(weatherData: WeatherData, iconMap: HashMap<String, Int>) {
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(8.dp)
    ){
        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.Top
        ) {
            items(weatherData.daily.data) {
                WeatherTile(it, iconMap)
            }
        }
    }

}

@Composable
fun CurrentWeatherDisplay(){
    Card (
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .padding(8.dp)

    ){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(),
            horizontalArrangement = Arrangement.Center
        ) {
            // Left half - Description
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .width(200.dp)
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.Start,
                    verticalArrangement = Arrangement.Top
                ){
                    Text(text = "35°", style = MaterialTheme.typography.titleLarge)
                    Text(text = "Sunny", style = MaterialTheme.typography.bodyMedium)
                }
                Column (
                    verticalArrangement = Arrangement.Bottom,
                    horizontalAlignment = Alignment.Start,
                    modifier = Modifier
                        .fillMaxSize()
                ){
                    Text(text = "Hatfield", style = MaterialTheme.typography.bodyMedium)
                    Text(text = "37° / 22° Feels like 27°", style = MaterialTheme.typography.bodySmall)
                }

            }
            Column (

            ){
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.cloud_24px),
                        contentDescription = "Test",
                        modifier = Modifier
                            .fillMaxSize()
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherTable(weatherData: Array<WeatherData.Daily.DailyData>, iconMap: HashMap<String, Int>){
    Card(
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primaryContainer
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(400.dp)
            .padding(8.dp)
    ){
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .height(100.dp)
                .padding(8.dp)
        ) {
            items(weatherData) {
                WeatherRow(it, iconMap)
            }
        }
    }
}

@Composable
fun WeatherTile(weatherData: WeatherData.Daily.DailyData, iconMap: HashMap<String, Int>){
    val icon = iconMap[weatherData.icon] ?: R.drawable.cloud_24px
    Column (
        modifier = Modifier
            .height(100.dp)
    ){
        Icon(
            painter = painterResource(icon),
            contentDescription = "Weather Image",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(12.dp),
            tint = contentColorFor(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = weatherData.temperatureMax.toString()
        )

    }
}

@Composable
fun WeatherRow(weatherData: WeatherData.Daily.DailyData, iconMap: HashMap<String, Int>){
    val icon = iconMap[weatherData.icon] ?: R.drawable.cloud_24px
    Row (
        modifier = Modifier
            .padding(5.dp)
            .fillMaxWidth()
    ){

        Column (
            horizontalAlignment = Alignment.Start
        ){
            Text(
                text = weatherData.temperatureHigh.toString()
            )
        }

        Spacer(modifier = Modifier.width(20.dp))
        Column(
            horizontalAlignment = Alignment.End,
            modifier = Modifier
                .fillMaxSize()
        ){
            Icon(
                painter = painterResource(icon),
                contentDescription = "Weather Image",
                modifier = Modifier
                    .size(60.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(12.dp),
                tint = contentColorFor(MaterialTheme.colorScheme.primary)
            )
        }
    }
}