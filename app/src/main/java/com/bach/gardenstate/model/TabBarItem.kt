package com.bach.gardenstate.model

import androidx.compose.ui.graphics.vector.ImageVector

data class TabBarItem(
    var title: String,
    var selectedIcon: ImageVector,
    var unselectedIcon: ImageVector,
    var badgeAmount: Int? = null
)
