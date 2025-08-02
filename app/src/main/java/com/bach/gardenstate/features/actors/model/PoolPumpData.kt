package com.bach.gardenstate.features.actors.model

import kotlinx.serialization.Serializable

@Serializable
data class PoolPumpData(
    val power: Int,
    val voltage: Int,
    var state: String,
    val linkquality: Int,
    val last_seen: String
)
