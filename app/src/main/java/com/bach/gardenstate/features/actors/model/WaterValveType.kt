package com.bach.gardenstate.features.actors.model

enum class WaterValveType {
    VEGETABLES,
    GREENHOUSE,
    RAISED_BED
}

val WaterValveType.friendlyName: String
    get() = when (this) {
        WaterValveType.VEGETABLES -> "Watervalve_Vegetables"
        WaterValveType.GREENHOUSE -> "Watervalve_Greenhouse"
        WaterValveType.RAISED_BED -> "Watervalve_RaisedBed"
    }

val WaterValveType.title: String
    get() = when (this) {
        WaterValveType.VEGETABLES -> "Wasserventil Gemüse"
        WaterValveType.GREENHOUSE -> "Wasserventil Gewächshaus"
        WaterValveType.RAISED_BED -> "Wasserventil Hochbeet"
    }