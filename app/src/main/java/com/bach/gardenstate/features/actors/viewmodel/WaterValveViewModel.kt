package com.bach.gardenstate.features.actors.viewmodel

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.actors.model.UIState
import com.bach.gardenstate.features.actors.model.WaterValveData
import kotlinx.serialization.json.Json

class WaterValveViewModel(private val waterValveFriendlyName: String) : ViewModel() {
    private val withUnknownKeys = Json { ignoreUnknownKeys = true }
    private val mqttServerUri: String = "tcp://192.168.188.21:1883"
    private val baseTopic: String = "zigbee2mqtt"
    private val interviewTopic: String = "$baseTopic/bridge/request/device/interview"

    private val _stateWaterValve: MutableState<Boolean> = mutableStateOf(
        false
    )
    val stateWaterValve: State<Boolean> = _stateWaterValve

    private val _messageWaterValve: MutableState<UIState> = mutableStateOf(
        UIState.isLoading
    )
    val messageWaterValve: State<UIState> = _messageWaterValve

    private val waterValveMqttClientManager = MqttClientManager(
        mqttServerUri,
        "$baseTopic/$waterValveFriendlyName/set"
    ) { message ->
        Log.d("WaterValveViewModel", "message: $message.value")
    }

    init {
        subscribeWaterValve()
        interview()
    }

    private fun interview() {
        val interviewMqttClientManager = MqttClientManager(mqttServerUri, interviewTopic)
        { message ->
            Log.d("OverviewScreenViewModel", "message: $message.value")
        }
        interviewMqttClientManager.publish("{\"id\": \"$waterValveFriendlyName\"}")
        interviewMqttClientManager.disconnect()
    }

    private fun subscribeWaterValve() {
        MqttClientManager(mqttServerUri, "$baseTopic/$waterValveFriendlyName")
        { message ->
            _messageWaterValve.value =
                UIState.success(withUnknownKeys.decodeFromString<WaterValveData>(message))
            val actualValue = _stateWaterValve.value
            val newValue =
                (_messageWaterValve.value as UIState.success).waterValveData.state == "ON"
            if (actualValue != newValue) {
                _stateWaterValve.value = newValue
            }
        }
    }

    fun onChangeWaterValveState(checked: Boolean) {
        if (checked) {
            waterValveMqttClientManager.publish("{\"state\":\"ON\"}")
        } else {
            waterValveMqttClientManager.publish("{\"state\":\"OFF\"}")
        }
    }
}