package com.bach.gardenstate.features.actors.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterValveData(
    val battery: Int,
    val irrigation_start_time: String,
    val last_irrigation_duration: String,
    val water_consumed: Int,
    var state: String,
    val linkquality: Int,
    val last_seen: String,

    )
