package com.switchcodeur.hardm3.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun SettingsIcon(filled: Boolean) {
    Icon(
        imageVector = if (filled) Icons.Filled.Settings else Icons.Outlined.Settings,
        contentDescription = "Settings"
    )
}