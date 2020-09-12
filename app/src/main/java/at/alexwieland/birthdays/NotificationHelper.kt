package at.alexwieland.birthdays

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationHelper() {
    companion object {
        const val CHANNEL_ID_BIRTHDAYS = "at.alexwieland.birthdays.birthdays"
    }

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O)
            return

        val name = context.getString(R.string.notification_channel_birthdays_name)
        val importance = NotificationManager.IMPORTANCE_DEFAULT
        val channel = NotificationChannel(CHANNEL_ID_BIRTHDAYS, name, importance)
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun showNotification(context: Context, content: String, id: Int) {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID_BIRTHDAYS)
            .setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(context.getString(R.string.app_name))
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        with(NotificationManagerCompat.from(context)) {
            notify(id, builder.build())
        }
    }
}