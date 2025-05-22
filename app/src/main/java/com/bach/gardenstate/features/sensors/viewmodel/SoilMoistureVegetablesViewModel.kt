package com.bach.gardenstate.features.sensors.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorData
import com.bach.gardenstate.features.sensors.model.SoilMoistureSensorUIState
import kotlinx.serialization.json.Json

class SoilMoistureVegetablesViewModel : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val interviewTopic: String = "zigbee2mqtt/bridge/request/device/interview"
    private val soilMoistureMqttTopic: String = "zigbee2mqtt/SoilMoistureSensor_Vegetables"

    private val _messageSoilMoistureSensor: MutableState<SoilMoistureSensorUIState> =
        mutableStateOf(
            SoilMoistureSensorUIState.isLoading
        )
    val messageSoilMoistureSensor: State<SoilMoistureSensorUIState> = _messageSoilMoistureSensor

    init {
        subscribeSoilMoistureSensor()
        interview()
    }

    private fun subscribeSoilMoistureSensor() {
        MqttClientManager(mqttServerUri, soilMoistureMqttTopic)
        { message ->
            _messageSoilMoistureSensor.value =
                SoilMoistureSensorUIState.success(
                    withUnknownKeys.decodeFromString<SoilMoistureSensorData>(
                        message
                    )
                )
        }
    }

    private fun interview() {
        val interviewMqttClientManager = MqttClientManager(mqttServerUri, interviewTopic)
        { message ->
            Log.d("OverviewScreenViewModel", "message: $message.value")
        }
        interviewMqttClientManager.publish("{\"id\": \"SoilMoistureSensor_Vegetables\"}")
        interviewMqttClientManager.disconnect()
    }
}