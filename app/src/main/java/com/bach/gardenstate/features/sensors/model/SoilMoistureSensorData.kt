package com.bach.gardenstate.features.sensors.model

import kotlinx.serialization.Serializable

@Serializable
data class SoilMoistureSensorData(
    val battery: Int,
    val last_seen: String,
    val linkquality: Int,
    val soil_moisture: Int,
    val temperature: Int
)
