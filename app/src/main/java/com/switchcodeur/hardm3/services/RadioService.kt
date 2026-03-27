package com.switchcodeur.hardm3.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Intent
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.net.wifi.WifiManager
import android.os.PowerManager
import android.widget.Toast
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.app.NotificationCompat
import com.switchcodeur.hardm3.R

class RadioService : Service() {
    private val url = "https://stream.hardcoreradio.nl:9000/hcr.ogg"
    private var wifiLock: WifiManager.WifiLock? = null

    companion object {
        var player: MediaPlayer? = null
        var isReady by mutableStateOf(false)
        var _isPlaying by mutableStateOf(false)
    }

    override fun onCreate() {
        super.onCreate()
        val wifiManager = getSystemService(WIFI_SERVICE) as WifiManager
        wifiLock = wifiManager.createWifiLock(WifiManager.WIFI_MODE_FULL_LOW_LATENCY, "radio_lock")
        wifiLock?.acquire()
        player = MediaPlayer().apply {
            setAudioAttributes(
                AudioAttributes.Builder()
                    .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                    .setUsage(AudioAttributes.USAGE_MEDIA)
                    .build()
            )
            setDataSource(url)
            setWakeMode(applicationContext, PowerManager.PARTIAL_WAKE_LOCK)
            setOnPreparedListener {
                isReady = true
                _isPlaying = true
                it.start()
            }
            setOnErrorListener { _, _, _ ->
                isReady = false
                _isPlaying = false
                Toast.makeText(applicationContext, "Stream error", Toast.LENGTH_LONG).show()
                true
            }
            prepareAsync()
        }
        startForeground(1, createNotification())
    }

    private fun createNotification(): Notification {
        val channelId = "radio_playback"
        val channel = NotificationChannel(
            channelId,
            "Radio Playback",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager.createNotificationChannel(channel)
        return NotificationCompat.Builder(this, channelId)
            .setContentText("Currently playing")
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setOngoing(true)
            .build()
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    override fun onDestroy() {
        player?.release()
        wifiLock?.release()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?) = null
}