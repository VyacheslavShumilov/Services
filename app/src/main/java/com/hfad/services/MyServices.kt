package com.hfad.services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Binder
import android.os.IBinder
import android.util.Log
import androidx.core.app.NotificationCompat


class MyServices : Service() {

    private val mBinder: IBinder = MyBinder()
    private val CHANNEL_ID = "Random number notification"
    private var mPlayer: MediaPlayer? = null

    inner class MyBinder : Binder() {
        val service: MyServices
            get() = this@MyServices
    }

    // TODO: 3
    override fun onCreate() {
        Log.d("START", "S onCreate()")

        mPlayer = MediaPlayer.create(this, R.raw.music)
        mPlayer!!.isLooping = true
        startNotification()
    }

    // TODO: 4
    private fun startNotification() {
        Log.d("START", "S startNotification()")

        val channel =
            NotificationChannel(
                CHANNEL_ID,
                "My Channel",
                NotificationManager.IMPORTANCE_DEFAULT
            )

        (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).createNotificationChannel(
            channel
        )
        val notification: Notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("A service is running in the background")
            .setContentText("Generating random number").build()
        startForeground(1, notification)
    }


    // TODO: 5
    override fun onBind(p0: Intent?): IBinder {
        Log.d("START", "S onBind()")

        return mBinder
    }


    // TODO: 6
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        Log.d("START", "S onStartCommand()")
        start()
        return START_STICKY
    }

    // TODO: 7
    private fun start() {
        Log.d("START", "S start()")
        mPlayer!!.start()
    }

    // TODO: 10
    override fun onDestroy() {
        Log.d("START", "S onDestroy()")
        mPlayer!!.stop()
        super.onDestroy()
    }




}