package com.bach.gardenstate.features.actors.screen

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.R
import com.bach.gardenstate.features.actors.model.AutomaticIrrigationData
import com.bach.gardenstate.features.actors.model.WaterValveType
import com.bach.gardenstate.features.actors.viewmodel.ActorScreenViewModel
import com.bach.gardenstate.features.actors.viewmodel.BaseViewModel
import com.bach.gardenstate.features.actors.views.PoolPumpView
import com.bach.gardenstate.features.actors.views.WaterValveView
import com.bach.gardenstate.features.data.ApiClient
import com.bach.gardenstate.model.TabBarItem
import com.bach.gardenstate.ui.views.navigation.TabView
import io.ktor.client.call.body
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ActorScreen(
    modifier: Modifier = Modifier,
    actorScreenViewModel: ActorScreenViewModel = viewModel(),
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
            val changeMenuTab = TabBarItem(
                title = "Aktoren",
                selectedIcon = ImageVector.vectorResource(R.drawable.valve_24px),
                unselectedIcon = ImageVector.vectorResource(R.drawable.valve_24px)
            )
            TabView(listOf(menuTab, changeMenuTab), 1) { tabTitle ->
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
            Card(
                modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            ) {
                Row(
                    Modifier.fillMaxWidth().padding(5.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Automatische Bewässerung",Modifier.padding(5.dp))

                    val isChecked : AutomaticIrrigationData = actorScreenViewModel.automaticIrrigation.value
                    Switch(
                        checked = isChecked.enabled,
                        onCheckedChange = {
                            actorScreenViewModel.onChangeAutomaticIrrigation(!isChecked.enabled)
                        }
                    )
                }
            }
            WaterValveView(waterValveType = WaterValveType.VEGETABLES)
            WaterValveView(waterValveType = WaterValveType.GREENHOUSE)
            WaterValveView(waterValveType = WaterValveType.RAISED_BED)
            PoolPumpView()
        }
    }
}