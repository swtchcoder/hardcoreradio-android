package com.switchcodeur.hardm3.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable

@Composable
fun HomeIcon(filled: Boolean) {
    Icon(
        imageVector = if (filled) Icons.Filled.Home else Icons.Outlined.Home,
        contentDescription = "Home"
    )
}