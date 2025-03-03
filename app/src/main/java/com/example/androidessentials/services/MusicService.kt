package com.example.androidessentials.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.Build
import android.os.IBinder
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.example.androidessentials.MainActivity
import com.example.androidessentials.R

class MusicService : Service() {

    private var mediaPlayer: MediaPlayer? = null
    private var isPlaying = false
    private val CHANNEL_ID = "MusicServiceChannel"

    private var currentTrackIndex = 0

    // Список треков
    private val trackList = listOf(
        R.raw.kenes_alimzhan_oz_zhureginnen_uyalmaisynba,
        R.raw.kosmuse_kezdesu_men_oshtasu,
        R.raw.kuandyk_rahym_senin_kulkinnen_aumaidy
    )

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification("Playing Music"))
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        when (intent?.action) {
            "ACTION_PLAY" -> startMusic()
            "ACTION_STOP" -> stopMusic()
            "ACTION_NEXT" -> playNext()
            "ACTION_PREVIOUS" -> playPrevious()
        }
        return START_STICKY
    }

    private fun startMusic() {
        if (!isPlaying) {
            mediaPlayer = MediaPlayer.create(applicationContext, trackList[currentTrackIndex])
            mediaPlayer?.isLooping = true
            mediaPlayer?.start()
            isPlaying = true
            startForeground(1, createNotification("Музыка играет 🎵"))
        }
    }


    private fun stopMusic() {
        mediaPlayer?.stop()
        mediaPlayer?.release()
        mediaPlayer = null
        isPlaying = false
        stopForeground(STOP_FOREGROUND_REMOVE)
    }

    private fun playNext() {
        if (currentTrackIndex < trackList.size - 1) {
            currentTrackIndex++
        } else {
            currentTrackIndex = 0
        }
        restartMusic()
    }

    private fun playPrevious() {
        if (currentTrackIndex > 0) {
            currentTrackIndex--
        } else {
            currentTrackIndex = trackList.size - 1
        }
        restartMusic()
    }

    private fun restartMusic() {
        stopMusic()
        startMusic()
    }

    private fun createNotification(text: String): Notification {
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this, 0, notificationIntent, PendingIntent.FLAG_IMMUTABLE)

        return NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Music Service")
            .setContentText(text)
            .setSmallIcon(R.drawable.ic_music_note)
            .setContentIntent(pendingIntent)
            .build()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel() {
        val serviceChannel = NotificationChannel(
            CHANNEL_ID,
            "Music Service Channel",
            NotificationManager.IMPORTANCE_LOW
        )
        val manager = getSystemService(NotificationManager::class.java)
        manager?.createNotificationChannel(serviceChannel)
    }

    override fun onDestroy() {
        stopMusic()
        super.onDestroy()
    }

    override fun onBind(intent: Intent?): IBinder? = null
}
