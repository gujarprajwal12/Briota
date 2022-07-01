package app.briota.sia.Front_End.UI

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.media.RingtoneManager
import androidx.core.app.NotificationCompat
import androidx.test.core.app.ApplicationProvider.getApplicationContext
import app.briota.sia.R


const val notificationID = 1
const val channelID = "channel1"
const val titleExtra = "titleExtra"
const val messageExtra = "Please follow your"


var notification = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
var r = RingtoneManager.getRingtone(getApplicationContext(), notification)


class Notification : BroadcastReceiver() {
    @SuppressLint("LaunchActivityFromNotification", "UnspecifiedImmutableFlag")
    override fun onReceive(context: Context, intent: Intent) {
        val myTitle = intent.getStringExtra(titleExtra)

        var resultpendingIntetn = Intent(context, DailyDairyActivity::class.java)


        var pendingIntent: PendingIntent = PendingIntent.getActivity(
            context, 0,
            resultpendingIntetn, PendingIntent.FLAG_UPDATE_CURRENT
        )

        val notification = NotificationCompat.Builder(context, channelID)
            .setSmallIcon(R.drawable.ic_launcher_foreground)
            .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.sia_dash_logo))
            .setContentIntent(pendingIntent)
            .setContentTitle(myTitle)
            .setVisibility(NotificationCompat.VISIBILITY_PRIVATE)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setAutoCancel(true)
            .build()

        val manager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        manager.notify(0, notification)


    }


}