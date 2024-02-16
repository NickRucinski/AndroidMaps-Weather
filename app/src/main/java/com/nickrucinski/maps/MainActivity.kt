package com.nickrucinski.maps

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.nickrucinski.maps.components.BottomNav
import com.nickrucinski.maps.components.NavigationGraph
import com.nickrucinski.maps.ui.theme.MapsTheme

class MainActivity : ComponentActivity() {


    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val url = ""
        val api = BuildConfig.WEATHER_API_KEY

        setContent {
            MapsTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    Scaffold(
                        bottomBar = { BottomNav(navController = navController) }
                    ) {  _ ->

                        NavigationGraph(navController = navController)
                    }


                }
            }
        }
    }
} // End of Main Activity

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Preview(showBackground = true)
@Composable
fun preview(){
    val iconMap = HashMap<String, Int>()
    iconMap["cloudy"] = R.drawable.cloud_24px
    iconMap["snow"] = R.drawable.weather_snowy_24px
//    val weatherData = ArrayList<WeatherData>()
//    weatherData.add(WeatherData("21.3", "cloudy"))
//    weatherData.add(WeatherData("22.3", "cloudy"))
//    weatherData.add(WeatherData("23.3", "cloudy"))
    MapsTheme {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            val navController = rememberNavController()
            Scaffold(
                bottomBar = { BottomNav(navController = navController) }
            ) {_ ->

                NavigationGraph(navController = navController)
            }


        }

    }
}