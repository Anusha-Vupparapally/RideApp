package com.example.rideapp.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.*
import com.example.rideapp.ui.screens.MapScreen
import com.example.rideapp.ui.screens.DropScreen

import com.example.rideapp.viewmodel.RideViewModel

@Composable
fun NavGraph(viewModel: RideViewModel) {

    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "map"
    ) {
        composable("map") {
            MapScreen(navController, viewModel)
        }

        composable("drop") {
            DropScreen(navController, viewModel)
        }
    }
}