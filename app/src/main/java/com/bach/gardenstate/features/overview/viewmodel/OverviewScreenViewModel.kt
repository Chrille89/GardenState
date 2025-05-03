package com.bach.gardenstate.features.overview.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.bach.gardenstate.MqttClientManager

class OverviewScreenViewModel : ViewModel() {

    private val  mqttServerUri = "tcp://192.168.188.21:1883"
    private val mqttTopic = "zigbee2mqtt/SoilMoistureSensor"

    private val _message : MutableState<String> = mutableStateOf("")
    val message : State<String> = _message

    private val mqttClientManager: MqttClientManager = MqttClientManager(mqttServerUri, mqttTopic) { message ->
        _message.value = message
    }



}