package com.bach.gardenstate.features.sensors.model

import kotlinx.serialization.Serializable

@Serializable
data class TemperatureSensorGreenhouse(
    val last_seen: String,
    val temperature: Float,
    val humidity: Float,
    val linkquality: Int,
)

