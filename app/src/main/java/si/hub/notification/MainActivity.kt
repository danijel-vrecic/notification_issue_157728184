package si.hub.notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.media.AudioAttributes
import android.net.Uri
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        findViewById<Button>(R.id.show_notification).setOnClickListener {
            createAndShowNotification()
        }
    }

    // https://issuetracker.google.com/issues/157728184
    private fun createAndShowNotification() {
        val channelId = "notification_channel"
        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(
            applicationContext, channelId
        ).apply {
            setSmallIcon(R.mipmap.ic_launcher)
            setDefaults(NotificationCompat.DEFAULT_ALL)
            setContentTitle("Notification")
            setContentText("Lorem Ipsum")
            setAutoCancel(true)
            priority = NotificationCompat.PRIORITY_MAX
        }
        if (notificationManager.getNotificationChannel(channelId) == null) {
            val notificationChannel = NotificationChannel(
                channelId, "Notification channel",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "This notification channel is used to notify the user."
                enableVibration(true)
                enableLights(true)

                // Custom sound
                val soundUri = Uri.parse("android.resource://${packageName}/${R.raw.o1}")
                val attributes = AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build()
                setSound(soundUri, attributes)
            }
            notificationManager.createNotificationChannel(notificationChannel)
        }
        val notification = builder.build()
        notificationManager.notify(0, notification)
    }
}