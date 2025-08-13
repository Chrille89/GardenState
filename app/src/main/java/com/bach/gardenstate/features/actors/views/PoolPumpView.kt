package com.bach.gardenstate.features.actors.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.bach.gardenstate.features.actors.model.PoolPumpUIState
import com.bach.gardenstate.features.actors.viewmodel.PoolPumpViewModel
import com.bach.gardenstate.features.actors.viewmodel.ViewModelFactory
import com.bach.gardenstate.utils.DateFormatter
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@Composable
fun PoolPumpView(
    modifier: Modifier = Modifier,
    poolPumpViewModel: PoolPumpViewModel = viewModel(
        factory = ViewModelFactory("PoolPump")
    ),
) {

    Card(
        modifier
            .fillMaxWidth()
            .padding(10.dp)
    ) {
        Text(
            "Poolpumpe", Modifier.padding(5.dp), style = MaterialTheme.typography.titleLarge
        )
        when (val poolPumpMessageState: PoolPumpUIState = poolPumpViewModel.messagePoolPump.value) {
            PoolPumpUIState.isLoading -> Column(
                Modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) { CircularProgressIndicator() }

            is PoolPumpUIState.success ->

                Column(
                    modifier = Modifier.padding(5.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp)
                ) {
                    Row(
                        Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Aktiv")
                        val isChecked = poolPumpViewModel.stateActor.value
                        Switch(checked = isChecked, onCheckedChange = {
                            poolPumpViewModel.onChangeState(!isChecked)
                        })
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Leistungsaufnahme")
                        Text("${poolPumpMessageState.poolPumpData.power} W")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Spannungsversorgung")
                        Text("${poolPumpMessageState.poolPumpData.voltage} V")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Gemessener Strom")
                        Text("${poolPumpMessageState.poolPumpData.current} A")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Energieverbrauch")
                        Text("${poolPumpMessageState.poolPumpData.energy} kWh")
                    }
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Verbindungs-Qualität")
                        Text("${poolPumpMessageState.poolPumpData.linkquality}")
                    }

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        val dateTime: LocalDateTime =
                            Instant.parse(poolPumpMessageState.poolPumpData.last_seen)
                                .toLocalDateTime(TimeZone.currentSystemDefault())
                        Text("Zuletzt Aktualisiert")
                        Text(DateFormatter.formatDateTime(dateTime))
                    }
                }
        }
    }
}