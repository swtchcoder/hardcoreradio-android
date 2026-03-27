package com.example.myapplication.pages

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun SettingsPage() {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {
        Text("Settings page")
    }
}