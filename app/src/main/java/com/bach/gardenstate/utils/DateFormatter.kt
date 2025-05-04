package com.bach.gardenstate.utils

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.byUnicodePattern

object DateFormatter {

    fun formatDateTime(time: LocalDateTime): String {
        val format = LocalDateTime.Format { byUnicodePattern("dd.MM.yy HH:mm") }
        return format.format(time)
    }
}