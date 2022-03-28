package com.example.foregroundserviceexample.services

import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import android.telecom.PhoneAccount.builder
import android.util.Log
import android.widget.Toast
import androidx.core.app.NotificationCompat
import com.example.foregroundserviceexample.R
import java.util.stream.Stream.builder

const val NOTIFICATION_CHANNEL_GENERAL="Checking"
const val INTENT_COMMAND="Command"
const val INTENT_COMMAND_REPLY = "Reply"

class MyService: Service() {
    override fun onBind(p0: Intent?): IBinder?=null

    override fun onCreate() {
        super.onCreate()
    }

    //Int returned decides what service does in the event system kills it
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {

        val command= intent?.getStringExtra(INTENT_COMMAND)
        if(command=="Stop") {
            stopService()
            return START_NOT_STICKY
        }
        showNotification()

        if(command == "Reply") {
            Toast.makeText(this,"Clicked in the notification", Toast.LENGTH_LONG).show()
        }

        return START_NOT_STICKY     //START_NOT_STICKY does not recreate service

                                //IF SERVICE IS ALREADY RUNNING, CALL onStart command again
                                //to recreate service, but with no message

                                //when you want media-player app to run independently use START_STICKY
                                //START_REDELIVER_INTENT when you want app to redeliver same intent
//        return super.onStartCommand(intent, flags, startId)
    }


    private fun stopService() {
        stopForeground(true)
        stopSelf()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun showNotification() {
        //local notification
        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val replyIntent = Intent(this,MyService::class.java).apply {
            putExtra(INTENT_COMMAND, INTENT_COMMAND_REPLY)
        }
        val replyPendingIntent= PendingIntent.getService(this,2,replyIntent,0)
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O) {      //'O' for 'Oreo'

            try {
                with(
                    NotificationChannel(
                        NOTIFICATION_CHANNEL_GENERAL,
                        "Hello Brandon",
                        NotificationManager.IMPORTANCE_DEFAULT
                    )) {
                    enableLights(true)
                    setShowBadge(true)
                    enableVibration(true)
                    setSound(null,null)     //can specify url of push sound
                    setDescription("Hello description")
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                    manager.createNotificationChannel(this)

                }
            } catch (e:Exception) {
                Log.d("Error displaying", "Show Notification ${e.localizedMessage}")
            }
        }
        with(
            NotificationCompat.Builder(this, NOTIFICATION_CHANNEL_GENERAL)
        ) {
            setContentTitle("First")
            setContentText("Notification text")
            setSmallIcon(R.drawable.ic_launcher_foreground)
            setContentIntent(replyPendingIntent)
            addAction(0,"Reply",replyPendingIntent)
            addAction(0,"Answer",replyPendingIntent)
            startForeground(1,build())
        }
    }
}