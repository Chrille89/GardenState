package com.bach.gardenstate.features.overview.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterValveData(
    val battery: Int,
    val last_seen: String,
    val linkquality: Int,
    val state: String,
    val water_consumed: Int
)
