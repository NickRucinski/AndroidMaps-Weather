package com.nickrucinski.maps.components

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.nickrucinski.maps.BottomNavItem

@Composable
fun NavigationGraph(navController: NavHostController) {
    NavHost(navController, startDestination = BottomNavItem.Home.screen_route) {
        composable(BottomNavItem.Home.screen_route) {
            WeatherScreen()
        }
        composable(BottomNavItem.Map.screen_route) {
            CustomMapView()
        }
        composable(BottomNavItem.Settings.screen_route) {
            WeatherScreen()
        }

    }
}

