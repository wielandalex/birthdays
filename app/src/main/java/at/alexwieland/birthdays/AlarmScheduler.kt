package at.alexwieland.birthdays

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import java.util.*

class AlarmScheduler(context: Context) {
    private val mContext: Context = context

    fun schedule() {
        val alarmManager = mContext.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        val intent = Intent(mContext, AlarmReceiver::class.java)

        if (alarmExists(intent))
            return

        val pendingIntent = PendingIntent.getBroadcast(mContext, 0, intent, 0)

        alarmManager.setInexactRepeating(
            AlarmManager.RTC,
            getTrigger().timeInMillis,
            AlarmManager.INTERVAL_DAY,
            pendingIntent
        )
    }

    private fun alarmExists(intent: Intent): Boolean {
        val pendingIntent = PendingIntent.getBroadcast(
            mContext, 0, intent, PendingIntent.FLAG_NO_CREATE)

        return pendingIntent != null
    }

    private fun getTrigger(): Calendar {
        return Calendar.getInstance().apply {
            timeInMillis = System.currentTimeMillis()
            set(Calendar.HOUR_OF_DAY, 8)
            set(Calendar.MINUTE, 0)
        }
    }
}