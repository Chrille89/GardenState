package com.bach.gardenstate.features.actors.model

enum class WaterValveType {
    VEGETABLES,
    GREENHOUSE
}

val WaterValveType.topic : String
    get() = when(this) {
        WaterValveType.VEGETABLES -> "Watervalve_Vegetables"
        WaterValveType.GREENHOUSE -> "Watervalve_Greenhouse"
    }

val WaterValveType.title : String
    get() = when(this) {
        WaterValveType.VEGETABLES -> "Wasserventil Gemüse"
        WaterValveType.GREENHOUSE -> "Wasserventil Gewächshaus"
    }