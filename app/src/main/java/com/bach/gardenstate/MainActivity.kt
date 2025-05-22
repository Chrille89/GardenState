package com.bach.gardenstate

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.bach.gardenstate.ui.theme.GardenStateTheme
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.bach.familyfresh.navigation.Routes
import com.bach.gardenstate.features.actors.screen.ActorScreen
import com.bach.gardenstate.features.sensors.screen.SensorScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GardenStateTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    NavHost(
                        navController,
                        startDestination = Routes.SensorMenuRoute
                    ) {
                        composable<Routes.SensorMenuRoute> {
                             SensorScreen { tabTitle ->
                                 when(tabTitle) {
                                     "Aktoren" ->  navController.navigate(Routes.ActorMenuRoute)
                                 }
                             }
                        }
                        composable<Routes.ActorMenuRoute> {
                                ActorScreen() {tabTitle ->
                            when(tabTitle) {
                                "Sensoren" ->  navController.navigate(Routes.SensorMenuRoute)
                            }
                        }
                        }
                    }
                }
            }
        }
    }
}
