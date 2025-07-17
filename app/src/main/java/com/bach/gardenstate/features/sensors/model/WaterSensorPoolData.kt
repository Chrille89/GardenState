package com.bach.gardenstate.features.sensors.model

import kotlinx.serialization.Serializable

@Serializable
data class WaterSensorPoolData(
    val battery: Int,
    val ec: Int,
    val ec_max: Int,
    val ec_min: Int,
    val free_chlorine: Float,
    val free_chlorine_max: Float,
    val free_chlorine_min: Int,
    val last_seen: String,
    val linkquality: Int,
    val orp: Int,
    val orp_max: Int,
    val orp_min: Int,
    val ph: Float,
    val ph_max: Int,
    val ph_min: Int,
    val salinity: Int,
    val tds: Int,
    val temperature: Float)
