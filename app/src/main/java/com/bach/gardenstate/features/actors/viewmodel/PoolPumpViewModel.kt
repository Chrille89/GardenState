package com.bach.gardenstate.features.actors.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.bach.gardenstate.MqttClientManager
import com.bach.gardenstate.features.actors.model.PoolPumpData
import com.bach.gardenstate.features.actors.model.PoolPumpUIState

class PoolPumpViewModel(private val poolPumpFriendlyName: String) :
    BaseViewModel(poolPumpFriendlyName) {

    private val _messagePoolPump: MutableState<PoolPumpUIState> = mutableStateOf(
        PoolPumpUIState.isLoading
    )
    val messagePoolPump: State<PoolPumpUIState> = _messagePoolPump

    override val defaultMsOnTime: Long
        get() = 14400000 // max 4h

    init {
        subscribe()
        interview()
    }

    override fun subscribe() {
        MqttClientManager(mqttServerUri, "$baseTopic/$poolPumpFriendlyName")
        { message ->
            _messagePoolPump.value =
                PoolPumpUIState.success(withUnknownKeys.decodeFromString<PoolPumpData>(message))
            val actualValue = _stateActor.value
            val newValue =
                (_messagePoolPump.value as PoolPumpUIState.success).poolPumpData.state == "ON"
            if (actualValue != newValue) {
                _stateActor.value = newValue
            }
        }
    }
}