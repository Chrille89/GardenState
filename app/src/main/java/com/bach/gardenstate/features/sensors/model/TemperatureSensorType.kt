package com.bach.gardenstate.features.sensors.model

import com.bach.gardenstate.features.actors.model.WaterValveType

enum class TemperatureSensorType {
    GREENHOUSE,
    FOILTENT
}

val TemperatureSensorType.friendlyName: String
    get() = when (this) {
        TemperatureSensorType.GREENHOUSE -> "TemperatureSensor_Greenhouse"
        TemperatureSensorType.FOILTENT -> "TemperatureSensor_FoilTent"
    }

val TemperatureSensorType.title: String
    get() = when (this) {
        TemperatureSensorType.GREENHOUSE -> "Temperatursensor Gewächshaus"
        TemperatureSensorType.FOILTENT -> "Temperatursensor Folienzelt"
    }

val TemperatureSensorType.endpoint: String
    get() = when (this) {
        TemperatureSensorType.GREENHOUSE -> "greenhouse"
        TemperatureSensorType.FOILTENT -> "foiltent"
    }