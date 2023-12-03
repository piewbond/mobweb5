package hu.bme.aut.android.jot

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import androidx.room.Room
import hu.bme.aut.android.jot.data.ExcerciseDatabase
import hu.bme.aut.android.jot.data.repository.ExcerciseRepositoryImpl
import hu.bme.aut.android.jot.notification.RemindersManager

class ExcerciseApplication : Application() {

    companion object {
        private lateinit var db: ExcerciseDatabase

        lateinit var repository: ExcerciseRepositoryImpl
    }

    override fun onCreate() {
        super.onCreate()

        createNotificationsChannels()
        
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        if (prefs.getBoolean("notificationsOn",false)) {
            RemindersManager.startReminder(this)
        }

        db = Room.databaseBuilder(
            applicationContext,
            ExcerciseDatabase::class.java,
            "excercise_database"
        ).fallbackToDestructiveMigration().build()

        repository = ExcerciseRepositoryImpl(db.dao)
    }
    private fun createNotificationsChannels() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                getString(R.string.reminders_notification_channel_id),
                getString(R.string.reminders_notification_channel_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            ContextCompat.getSystemService(this, NotificationManager::class.java)
                ?.createNotificationChannel(channel)
        }
    }
}