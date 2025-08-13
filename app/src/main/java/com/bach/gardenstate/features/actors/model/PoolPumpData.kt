package com.bach.gardenstate.features.actors.model

import kotlinx.serialization.Serializable

@Serializable
data class PoolPumpData(
    var state: String,
    val power: Int,
    val voltage: Int,
    val current: Int,
    val energy: Float,
    val linkquality: Int,
    val last_seen: String
)
