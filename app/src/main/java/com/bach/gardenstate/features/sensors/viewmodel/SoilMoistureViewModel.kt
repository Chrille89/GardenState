package com.bach.gardenstate.features.sensors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bach.gardenstate.features.data.ApiClient
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorData
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorUIState
import com.bach.gardenstate.features.sensors.model.SoilMoistureType
import io.ktor.client.call.body
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json

class SoilMoistureViewModel(private var soilMoistureType: SoilMoistureType) : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val _messageSoilMoisture: MutableState<SoilMoistureSensorUIState> = mutableStateOf(
        SoilMoistureSensorUIState.isLoading
    )
    val messageSoilMoisture: State<SoilMoistureSensorUIState> = _messageSoilMoisture

    val sensorTypeString: String
        get() = when (soilMoistureType) {
            SoilMoistureType.VEGETABLES -> "Bodenfeuchte Gemüse"
            SoilMoistureType.GREENHOUSE -> "Bodenfeuchte Gewächshaus"
        }

    init {
        viewModelScope.launch {
            val httpResponse = when (soilMoistureType) {
                SoilMoistureType.VEGETABLES -> ApiClient.getActualSoilMoistureVegetables()
                SoilMoistureType.GREENHOUSE -> ApiClient.getActualSoilMoistureGreenHouse()
            }
            when (httpResponse.status.value) {
                200 -> _messageSoilMoisture.value =
                    SoilMoistureSensorUIState.success(
                        withUnknownKeys.decodeFromString<SoilMoistureSensorData>(
                            httpResponse.body()
                        )
                    )
            }
        }
    }
}