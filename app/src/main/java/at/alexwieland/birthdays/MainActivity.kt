package at.alexwieland.birthdays

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.main_activity)

        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.test_fragment, TestFragment())
            }.commit()
        }

        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), 0)

        NotificationHelper().createChannels(this)

        AlarmScheduler(this).schedule()
    }
}