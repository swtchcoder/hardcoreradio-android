package com.switchcodeur.hardm3.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Pause
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun PlayIcon(toggled: Boolean) {
    if (toggled) {
        Icon(
            imageVector = Icons.Filled.Pause,
            contentDescription = "Pause"
        )
    } else {
        Icon(
            imageVector = Icons.Filled.PlayArrow,
            contentDescription = "Play"
        )
    }
}