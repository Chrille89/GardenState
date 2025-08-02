package com.bach.gardenstate.features.actors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.actors.model.WaterValveUIState
import com.bach.gardenstate.features.actors.model.WaterValveData

class WaterValveViewModel(private val waterValveFriendlyName: String) : BaseViewModel(waterValveFriendlyName) {

    private val _messageWaterValve: MutableState<WaterValveUIState> = mutableStateOf(
        WaterValveUIState.isLoading
    )
    val messageWaterValve: State<WaterValveUIState> = _messageWaterValve

    init {
        subscribe()
        interview()
    }

    override fun subscribe() {
        MqttClientManager(mqttServerUri, "$baseTopic/$waterValveFriendlyName")
        { message ->
            _messageWaterValve.value =
                WaterValveUIState.success(withUnknownKeys.decodeFromString<WaterValveData>(message))
            val actualValue = _stateActor.value
            val newValue =
                (_messageWaterValve.value as WaterValveUIState.success).waterValveData.state == "ON"
            if (actualValue != newValue) {
                _stateActor.value = newValue
            }
        }
    }
}