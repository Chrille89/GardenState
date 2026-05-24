package com.bach.gardenstate.features.sensors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bach.gardenstate.features.data.ApiClient
import com.bach.gardenstate.features.sensors.model.TemperatureSensorData
import com.bach.gardenstate.features.sensors.model.TemperatureSensorUIState
import io.ktor.client.call.body
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class TemperatureSensorViewModel(private val endpoint: String) : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val _messageTemperatureSensor: MutableState<TemperatureSensorUIState> = mutableStateOf(
        TemperatureSensorUIState.isLoading
    )
    val messageTemperatureSensor: State<TemperatureSensorUIState> = _messageTemperatureSensor

    init {
        viewModelScope.launch {
            val httpResponse = ApiClient.getActualTemperature(endpoint)
            when (httpResponse.status.value) {
                200 -> _messageTemperatureSensor.value =
                    TemperatureSensorUIState.success(
                        withUnknownKeys.decodeFromString<TemperatureSensorData>(
                            httpResponse.body()
                        )
                    )
            }
        }
    }
}