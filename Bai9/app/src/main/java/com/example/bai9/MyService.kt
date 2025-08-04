package com.example.bai9

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder

class MyService : Service() {
    private var mymedia: MediaPlayer? = null

    override fun onBind(intent: Intent?): IBinder? {
        // Not used in this example
        throw UnsupportedOperationException("Not yet implemented")
    }

    override fun onCreate() {
        super.onCreate()
        mymedia = MediaPlayer.create(this, R.raw.sally_shigeaki_saegusa)
        mymedia?.isLooping = true
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mymedia?.let {
            if (it.isPlaying) {
                it.pause()
            } else {
                it.start()
            }
        }
        return super.onStartCommand(intent, flags, startId)
    }

    override fun onDestroy() {
        super.onDestroy()
        mymedia?.stop()
        mymedia?.release()
        mymedia = null
    }
}