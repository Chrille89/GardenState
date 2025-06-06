package com.bach.gardenstate.features.actors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.actors.model.UIState
import com.bach.gardenstate.features.actors.model.WaterValveType
import com.bach.gardenstate.features.actors.model.friendlyName
import com.bach.gardenstate.features.actors.model.title
import com.bach.gardenstate.features.actors.viewmodel.WaterValveViewModel
import com.bach.gardenstate.features.actors.viewmodel.WaterValveViewModelFactory
import com.bach.gardenstate.ui.theme.GardenStateTheme
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun WaterValveView(
    modifier: Modifier = Modifier,
    waterValveType: WaterValveType,
    waterValveViewModel: WaterValveViewModel = viewModel(
        key = waterValveType.friendlyName,
        factory = WaterValveViewModelFactory(waterValveType.friendlyName)
    ),
) {

    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            waterValveType.title,
            Modifier.padding(5.dp),
            style = MaterialTheme.typography.titleLarge
        )
        when (val waterValveMessageState: UIState = waterValveViewModel.messageWaterValve.value) {
            is UIState.success ->
                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {

                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Bewässerung")
                        val isChecked = waterValveMessageState.waterValveData.state == "ON"
                        Switch(
                            checked = isChecked,
                            onCheckedChange = {
                                waterValveViewModel.onChangeWaterValveState(!isChecked)
                            }
                        )
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Letzte Bewässerung")
                        Text(waterValveMessageState.waterValveData.irrigation_start_time)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Letzte Bewässerungsdauer")
                        Text(waterValveMessageState.waterValveData.last_irrigation_duration)
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Letzte Bewässerungsmenge")
                        Text("${waterValveMessageState.waterValveData.water_consumed} Liter")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Verbindungs-Qualität")
                        Text("${waterValveMessageState.waterValveData.linkquality}")
                    }
                    Column {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("Akku Sensor")
                            Text("${waterValveMessageState.waterValveData.battery} %")
                        }
                        Row {
                            LinearProgressIndicator(
                                modifier = Modifier.fillMaxWidth(),
                                progress = { waterValveMessageState.waterValveData.battery.toFloat() / 100 })
                        }
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dateTime: LocalDateTime =
                            Instant.parse(waterValveMessageState.waterValveData.last_seen)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        Text("Zuletzt Aktualisiert")
                        Text(DateFormatter.formatDateTime(dateTime))
                    }
                }
            else -> {
                Column(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) { CircularProgressIndicator() }
            }
        }
    }

}


@Preview
@Composable
fun WaterValveViewPreview() {
    GardenStateTheme {
        WaterValveView(Modifier, WaterValveType.VEGETABLES)
    }
}