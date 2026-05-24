package com.bach.gardenstate.features.actors.model

import kotlinx.serialization.Serializable

@Serializable
data class AutomaticIrrigationData(
    var enabled: Boolean
) {
}