package com.example.myapplication.pages

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.myapplication.components.MediaVisualizer
import com.example.myapplication.components.PlayIcon
import com.example.myapplication.services.RadioService

@Composable
fun HomePage() {
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        MediaVisualizer(RadioService._isPlaying)
        PlayButton()
    }
}

@Composable
fun PlayButton() {
    Button(
        modifier = Modifier.padding(top = 12.dp, bottom = 24.dp),
        onClick = {
            if (!RadioService.isReady) {
                return@Button
            }
            if (RadioService._isPlaying) {
                RadioService.player?.pause()
            } else {
                RadioService.player?.start()
            }
            RadioService._isPlaying = !RadioService._isPlaying
        },
    ) {
        PlayIcon(RadioService._isPlaying)
    }
}