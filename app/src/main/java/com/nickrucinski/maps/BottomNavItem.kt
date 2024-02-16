package com.nickrucinski.maps

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Place
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.graphics.vector.ImageVector


sealed class BottomNavItem(var title:String, var icon: ImageVector, var screen_route:String){

    object Home : BottomNavItem("Home", Icons.Default.Home,"home")
    object Map: BottomNavItem("Map",Icons.Default.Place,"map")
    object Settings: BottomNavItem("Settings",Icons.Default.Settings,"settings")
}