package com.bach.gardenstate.features.actors.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import com.bach.gardenstate.R
import com.bach.gardenstate.features.actors.model.WaterValveType
import com.bach.gardenstate.features.actors.views.WaterValveView
import com.bach.gardenstate.features.sensors.views.GreenhouseView
import com.bach.gardenstate.features.sensors.views.VegetablesView
import com.bach.gardenstate.model.TabBarItem
import com.bach.gardenstate.ui.views.navigation.TabView
import com.bach.gardenstate.utils.DateFormatter

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorScreen(
    modifier: Modifier = Modifier,
    onClickTab: (title: String) -> Unit
) {
    Scaffold(
        modifier,
        topBar = {
            CenterAlignedTopAppBar(
                title = { Text("Aktoren") }
            )
        },
        bottomBar = {
            val menuTab = TabBarItem(
                title = "Sensoren",
                selectedIcon = ImageVector.vectorResource(R.drawable.baseline_sensors_24),
                unselectedIcon = ImageVector.vectorResource(R.drawable.baseline_sensors_24)
            )
            val changeMenuTab =  TabBarItem(
                title = "Aktoren",
                selectedIcon = ImageVector.vectorResource(R.drawable.valve_24px),
                unselectedIcon = ImageVector.vectorResource(R.drawable.valve_24px)
            )
            TabView(listOf(menuTab, changeMenuTab),1) { tabTitle ->
                onClickTab(tabTitle)
            }
        }
    ) { paddings ->
        Column(
            Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            WaterValveView(waterValveType = WaterValveType.VEGETABLES)
            WaterValveView(waterValveType= WaterValveType.GREENHOUSE)
        }
    }
}