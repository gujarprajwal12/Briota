package app.briota.sia.Front_End.UI

import android.app.Notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import app.briota.sia.R
import eu.findair.findairble.managers.FindAirSDK
import eu.findair.findairble.utils.FindAirSDKBuilder
import java.util.*

class BootReceiver : BroadcastReceiver(){

    lateinit var findAirSDK: FindAirSDK
    lateinit var context : Context

    override fun onReceive(p0: Context?, p1: Intent?) {

        Log.d("findairone", "received in library")


        findAirSDK = FindAirSDKBuilder(context.applicationContext)
            .setUsagesListener { usage ->
                val notificationBuilder: NotificationCompat.Builder =
                    NotificationCompat.Builder(context)
                        .setChannelId("findair")
                        .setSmallIcon(R.drawable.ic_launcher_foreground_2)
                        .setContentText(usage.macAddress + " New puff: " + usage.date)
                        .setGroup("PUFFS")
                        .setOngoing(false)
                        .setPriority(Notification.PRIORITY_MIN)
                val notification = notificationBuilder.build()
                val notificationManager = NotificationManagerCompat.from(context)
                notificationManager.notify(Date().time.toInt(), notification)
            }.setDevListener { }.setPhoneStateListener { }.build()
        findAirSDK.init()

    }


}