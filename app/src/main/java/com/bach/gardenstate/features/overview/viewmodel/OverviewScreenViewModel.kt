package com.bach.gardenstate.features.overview.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.overview.model.SoilMoistureSensorData
import com.bach.gardenstate.features.overview.model.TemperatureSensorGreenhouse
import com.bach.gardenstate.features.overview.model.WaterValveData
import kotlinx.serialization.json.Json

class OverviewScreenViewModel : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val interviewTopic: String = "zigbee2mqtt/bridge/request/device/interview"
    private val soilMoistureMqttTopic: String = "zigbee2mqtt/SoilMoistureSensor_Vegetables"
    private val temperatureSensorGreenHouseMqttTopic: String =
        "zigbee2mqtt/TemperatureSensor_Greenhouse"
    private val waterValveMqttTopic: String = "zigbee2mqtt/Watervalve_Vegetables"

    private val _messageWaterValveSensor: MutableState<WaterValveData> = mutableStateOf(
        WaterValveData(50, "2025-05-03T22:27:46+02:00", 32, "OFF", 100)
    )
    val messageWaterValveSensor: State<WaterValveData> = _messageWaterValveSensor

    private val _messageSoilMoistureSensor: MutableState<SoilMoistureSensorData> = mutableStateOf(
        SoilMoistureSensorData(50, "2025-05-03T22:27:46+02:00", 32, 38, 20)
    )
    val messageSoilMoistureSensor: State<SoilMoistureSensorData> = _messageSoilMoistureSensor

    private val _messageTemperatureSensorGreenhouse: MutableState<TemperatureSensorGreenhouse> =
        mutableStateOf(
            TemperatureSensorGreenhouse(
                "2025-05-03T22:27:46+02:00",
                29.02f,
                humidity = 99.05f,
                linkquality = 50
            )
        )
    val messageTemperatureSensorGreenhouse: State<TemperatureSensorGreenhouse> =
        _messageTemperatureSensorGreenhouse

    private val waterValveMqttClientManager = MqttClientManager(
        mqttServerUri,
        "$waterValveMqttTopic/set"
    ) { message ->
        Log.d("OverviewScreenViewModel", "message: $message.value")

    }

    init {
        subscribeWaterValve()
        subscribeSoilMoistureSensor()
        subscribeTemperatureSensorGreenHouse()
        interviewMqttDevices()
    }

    private fun subscribeWaterValve() {
        MqttClientManager(mqttServerUri, waterValveMqttTopic)
        { message ->
            _messageWaterValveSensor.value =
                withUnknownKeys.decodeFromString<WaterValveData>(message)
        }
    }

    private fun subscribeSoilMoistureSensor() {
        MqttClientManager(mqttServerUri, soilMoistureMqttTopic)
        { message ->
            _messageSoilMoistureSensor.value =
                withUnknownKeys.decodeFromString<SoilMoistureSensorData>(message)
        }
    }

    private fun subscribeTemperatureSensorGreenHouse() {
        MqttClientManager(mqttServerUri, temperatureSensorGreenHouseMqttTopic)
        { message ->
            Log.d("OverviewScreenViewModel", message)
            _messageTemperatureSensorGreenhouse.value =
                withUnknownKeys.decodeFromString<TemperatureSensorGreenhouse>(message)
        }
    }

    private fun interviewMqttDevices() {
        val interviewMqttClientManager = MqttClientManager(mqttServerUri, interviewTopic)
        { message ->
            Log.d("OverviewScreenViewModel", "message: $message.value")
        }
        interviewMqttClientManager.publish("{\"id\": \"Watervalve_Vegetables\"}")
        interviewMqttClientManager.publish("{\"id\": \"SoilMoistureSensor_Vegetables\"}")
        interviewMqttClientManager.disconnect()
    }

    fun onChangeWaterValveState(checked: Boolean) {
        if (checked) {
            waterValveMqttClientManager.publish("{\"state\":\"ON\"}")
        } else {
            waterValveMqttClientManager.publish("{\"state\":\"OFF\"}")
        }
    }
}