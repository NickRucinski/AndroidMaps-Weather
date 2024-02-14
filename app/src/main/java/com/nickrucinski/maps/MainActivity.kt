package com.nickrucinski.maps

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.combinedClickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.AbsoluteAlignment
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.CameraPositionState
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.MapProperties
import com.google.maps.android.compose.MapType
import com.google.maps.android.compose.MapUiSettings
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.MarkerState
import com.nickrucinski.maps.ui.theme.MapsTheme
import org.json.JSONObject

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
                    var visible by remember { mutableStateOf(false) }
                    Column {
                        AnimatedVisibility(
                            visible = visible,
                            enter = fadeIn(tween()),
                            exit = fadeOut(tween(200))
                        ) {
                            CustomMapView()
                        }
                        AnimatedVisibility(
                            visible = !visible,
                            enter = fadeIn(tween()),
                            exit = fadeOut(tween(200))
                        ) {
                            Column {
                                CurrentWeatherDisplay()
                                WeatherBar(weatherData = weatherData, iconMap = iconMap)
                            }

                        }
                    }
                    Box {
                        BottomBar(modifier=Modifier.align(Alignment.BottomCenter))
                        IconButton(
                            onClick = {
                                visible = visible.not()
                                Log.d("",visible.toString())
                                      },
                            modifier = Modifier
                                .padding(bottom = 35.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .align(Alignment.BottomCenter)
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

@Preview(showBackground = true)
@Composable
fun preview(){
    val iconMap = HashMap<String, Int>()
    iconMap["cloudy"] = R.drawable.cloud_24px
    iconMap["snow"] = R.drawable.weather_snowy_24px
    MapsTheme {
        Column {
            CurrentWeatherDisplay()
            //WeatherBar(weatherData = , iconMap = iconMap)
            Box {
                BottomBar(modifier=Modifier.align(Alignment.BottomCenter))
                IconButton(
                    onClick = {

                    },
                    modifier = Modifier
                        .padding(bottom = 35.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.secondary)
                        .align(Alignment.BottomCenter)
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

    }
}

@Composable
fun WeatherBar(weatherData: ArrayList<WeatherData>, iconMap: HashMap<String, Int>) {
    LazyRow(
        modifier = Modifier
            .fillMaxWidth()
            .height(100.dp)
            .background(MaterialTheme.colorScheme.background)
            .padding(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
        verticalAlignment = Alignment.Top
    ) {
        items(weatherData) {
            WeatherTile(it, iconMap)
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
fun WeatherTable(weatherData: ArrayList<WeatherData>, iconMap: HashMap<String, Int>){

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

@Composable
fun BottomBar(modifier: Modifier = Modifier) {

    var navNum by remember {
        mutableStateOf(0)
    }

    Row(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(topStart = 30.dp, topEnd = 30.dp))
            .background(Color.Black)
            .padding(vertical = 15.dp, horizontal = 15.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (navNum == 0) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }

            } else {
                IconButton(onClick = { navNum = 0 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier
                            .size(25.dp)
                    )
                }
            }


            Spacer(modifier = Modifier.width(8.dp))
            if (navNum == 1) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            } else {
                IconButton(onClick = { navNum = 1 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            if (navNum == 2) {

                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            } else {
                IconButton(onClick = { navNum = 2 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.width(8.dp))
            if (navNum == 3) {
                IconButton(onClick = { /*TODO*/ }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.secondary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            } else {
                IconButton(onClick = { navNum = 3 }) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_launcher_foreground),
                        contentDescription = "home",
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(25.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun CustomMapView(){
    var uiSettings by remember {
        mutableStateOf(MapUiSettings())
    }
    var properties by remember {
        mutableStateOf(MapProperties(mapType = MapType.HYBRID))
    }

    Box(Modifier.fillMaxSize()) {
        GoogleMap(
            modifier = Modifier.matchParentSize(),
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
    }
}