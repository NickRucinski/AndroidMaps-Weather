package com.nickrucinski.maps

import android.content.Context
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.nickrucinski.maps.ui.theme.MapsTheme
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader

class MainActivity : ComponentActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val iconMap = createIconMap()
        val url = ""
        val api = com.nickrucinski.maps.BuildConfig.WEATHER_API_KEY
        val json = JSONObject(Utils().ReadJSONFromAssets(this, "response.json"))
        Log.d("",json.getString("latitude"))
        val weatherData = ArrayList<WeatherData>()
        val icons = ArrayList<String>()

        val tempsJSON = json.getJSONObject("daily").getJSONArray("data")
        for(i in 0 until tempsJSON.length()){
            weatherData.add(
                WeatherData(
                    ((tempsJSON[i] as JSONObject).getString("temperatureMax")),
                    ((tempsJSON[i] as JSONObject).getString("icon"))
                )
            )
        }
        setContent {
            MapsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    WeatherBar(weatherData, iconMap)

                }
            }
        }
    }

    fun createIconMap(): HashMap<String, Int>{
        val iconMap = HashMap<String, Int>()
        iconMap["cloudy"] = R.drawable.cloud_24px
        iconMap["snow"] = R.drawable.weather_snowy_24px
        return iconMap
    }
} // End of Main Activity


@Composable
fun WeatherBar(weatherData: ArrayList<WeatherData>, iconMap: HashMap<String, Int>){

    LazyRow (
        modifier = Modifier
            .fillMaxWidth()
            .height(72.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ){
        items(weatherData){
            WeatherTile(it, iconMap)
        }

    }
}

@Composable
fun WeatherTile(weatherData: WeatherData, iconMap: HashMap<String, Int>){
    val icon = iconMap[weatherData.icon] ?: R.drawable.cloud_24px
    Column {
        Icon(
            painter = painterResource(icon),
            contentDescription = "Weather Image",
            modifier = Modifier
                .size(48.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(MaterialTheme.colorScheme.primary)
                .padding(12.dp),
            tint = contentColorFor(MaterialTheme.colorScheme.primary)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = weatherData.temperatureMax
        )

    }
}