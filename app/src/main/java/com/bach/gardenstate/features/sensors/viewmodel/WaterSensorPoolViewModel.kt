package com.bach.gardenstate.features.sensors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bach.gardenstate.features.sensors.data.ApiClient
import com.bach.gardenstate.features.sensors.model.WaterSensorPoolData
import com.bach.gardenstate.features.sensors.model.WaterSensorPoolUIState
import io.ktor.client.call.body
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class WaterSensorPoolViewModel : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val _messageWaterSensorPool: MutableState<WaterSensorPoolUIState> = mutableStateOf(
        WaterSensorPoolUIState.isLoading
    )
    val messageWaterSensorPool: State<WaterSensorPoolUIState> = _messageWaterSensorPool

    init {
        viewModelScope.launch {
            val httpResponse = ApiClient.getActualPoolWaterQuality()
            when (httpResponse.status.value) {
                200 -> _messageWaterSensorPool.value =
                    WaterSensorPoolUIState.success(
                        withUnknownKeys.decodeFromString<WaterSensorPoolData>(
                            httpResponse.body()
                        )
                    )
            }
        }
    }
}