package com.bach.gardenstate.features.sensors.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.tooling.preview.Preview
import com.bach.gardenstate.R
import com.bach.gardenstate.features.sensors.model.SoilMoistureType
import com.bach.gardenstate.features.sensors.model.TemperatureSensorType
import com.bach.gardenstate.features.sensors.views.SoilMoistureSensorView
import com.bach.gardenstate.features.sensors.views.TemperatureSensorGreenhouseView
import com.bach.gardenstate.features.sensors.views.TemperatureSensorView
import com.bach.gardenstate.features.sensors.views.WaterSensorPoolView
import com.bach.gardenstate.model.TabBarItem
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.ui.views.navigation.TabView

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SensorScreen(
    modifier: Modifier = Modifier,
    onClickTab: (title: String) -> Unit
) {
    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Sensoren") }
            )
        },
        bottomBar = {
            val menuTab = TabBarItem(
                title = "Sensoren",
                selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sensors_24),
                unselectedIcon = ImageVector.vectorResource(R.drawable.baseline_sensors_24)
            )
            val changeMenuTab = TabBarItem(
                title = "Aktoren",
                selectedIcon = ImageVector.vectorResource(R.drawable.valve_24px),
                unselectedIcon = ImageVector.vectorResource(R.drawable.valve_24px)
            )
            TabView(listOf(menuTab, changeMenuTab), 0) { tabTitle ->
                onClickTab(tabTitle)
            }
        }
    ) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
                .verticalScroll(rememberScrollState())
        ) {
            SoilMoistureSensorView(soilMoistureType = SoilMoistureType.VEGETABLES)
            SoilMoistureSensorView(soilMoistureType = SoilMoistureType.GREENHOUSE)
            TemperatureSensorView(temperatureSensorType = TemperatureSensorType.GREENHOUSE)
            TemperatureSensorView(temperatureSensorType = TemperatureSensorType.FOILTENT)

            WaterSensorPoolView()
        }
    }
}

@Composable
@Preview
fun SensorScreenPreview() {
    GardenStateTheme {
        SensorScreen {}
    }
}