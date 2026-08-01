package com.example.smartdisplay.service

import android.content.ComponentName
import android.media.AudioManager
import android.media.session.MediaController
import android.media.session.MediaSessionManager
import android.service.notification.NotificationListenerService
import android.view.KeyEvent

class MediaNotificationListenerService : NotificationListenerService() {

    private lateinit var mediaSessionManager: MediaSessionManager

    override fun onCreate() {
        super.onCreate()
        mediaSessionManager = getSystemService(MEDIA_SESSION_SERVICE) as MediaSessionManager
    }

    /**
     * Envía una orden universal de PLAY/PAUSE al sistema.
     * Funciona aunque la aplicación origen lleve horas en segundo plano.
     */
    fun togglePlayPauseUniversal() {
        val controllers = mediaSessionManager.getActiveSessions(
            ComponentName(this, MediaNotificationListenerService::class.java)
        )

        if (controllers.isNotEmpty()) {
            val activeController = controllers[0]
            val state = activeController.playbackState?.state
            if (state == android.media.session.PlaybackState.STATE_PLAYING) {
                activeController.transportControls.pause()
            } else {
                activeController.transportControls.play()
            }
        } else {
            // Fallback: Enviar KeyEvent global de MEDIA_PLAY al sistema Android
            val audioManager = getSystemService(AUDIO_SERVICE) as AudioManager
            audioManager.dispatchMediaKeyEvent(
                KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_MEDIA_PLAY)
            )
            audioManager.dispatchMediaKeyEvent(
                KeyEvent(KeyEvent.ACTION_UP, KeyEvent.KEYCODE_MEDIA_PLAY)
            )
        }
    }
}