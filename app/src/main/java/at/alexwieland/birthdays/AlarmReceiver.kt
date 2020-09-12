package at.alexwieland.birthdays

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import java.time.LocalDate

class AlarmReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        Log.d(AlarmReceiver::class.java.canonicalName, "onReceive")
        val birthdays = BirthdayLoader().getBirthdays(context)

        val now = LocalDate.now()

        for (birthday in birthdays) {
            if (birthday == null)
                continue

            if (birthday.date.withYear(now.year) != now)
                continue

            val age = now.year - birthday.date.year

            val content = if (age > 0) {
                context.getString(R.string.notification_birthday_text_age, birthday.name, age)
            } else {
                context.getString(R.string.notification_birthday_text, birthday.name)
            }

            NotificationHelper().showNotification(context, content, birthday.id)
        }
    }
}