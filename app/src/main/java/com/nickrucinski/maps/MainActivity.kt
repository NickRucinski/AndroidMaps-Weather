package com.nickrucinski.maps

import android.content.Context
import android.os.Bundle
import android.util.JsonReader
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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

    lateinit var iconMap: HashMap<String, Int>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        iconMap = HashMap<String, Int>()
        iconMap["cloudy"] = R.drawable.cloud_24px
        iconMap["snow"] = R.drawable.weather_snowy_24px
        val url = ""
        val api = com.nickrucinski.maps.BuildConfig.WEATHER_API_KEY
        val json = JSONObject(Utils().ReadJSONFromAssets(this, "response.json"))
        Log.d("",json.getString("latitude"))
        val temps = ArrayList<String>()
        val icons = ArrayList<String>()

        val tempsJSON = json.getJSONObject("daily").getJSONArray("data")
        for(i in 0 until tempsJSON.length()){
            temps.add(((tempsJSON[i] as JSONObject).getString("temperatureMax")))
            icons.add(((tempsJSON[i] as JSONObject).getString("icon")))
        }
        setContent {
            MapsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                    WeatherBar(temps, icons)

                }
            }
        }
    }
} // End of Main Activity

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}


@Composable
fun WeatherBar(temps: List<String>, icons: List<String>){

    LazyRow (
        modifier = Modifier.padding(all = 2.dp)
    ){
        items(temps){
            WeatherTile(it, icons)
        }

    }


}

@Composable
fun WeatherTile(temperature: String, iconName: String){
    val icon = MainActivity().iconMap[iconName] ?: R.drawable.cloud_24px
    Column {
        Image(
            painter = painterResource(icon),
            contentDescription = "Weather Image",
            modifier = Modifier
                .size(120.dp)
                .clip(CircleShape)
        )
        Spacer(modifier = Modifier.width(20.dp))

        Text(
            text = temperature
        )

    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    val temps = ArrayList<String>()
    temps.add("")
    temps.add("")
    temps.add("")

    MapsTheme {
        //Greeting("Android")
        WeatherBar(temps)
    }
}