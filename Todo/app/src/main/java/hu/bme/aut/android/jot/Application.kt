package hu.bme.aut.android.jot

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.preference.PreferenceManager
import androidx.core.content.ContextCompat
import androidx.room.Room
import hu.bme.aut.android.jot.data.TodoDatabase
import hu.bme.aut.android.jot.data.repository.TodoRepositoryImpl
import hu.bme.aut.android.jot.notification.RemindersManager

class TodoApplication : Application() {

    companion object {
        private lateinit var db: TodoDatabase

        lateinit var repository: TodoRepositoryImpl
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
            TodoDatabase::class.java,
            "excercise_database"
        ).fallbackToDestructiveMigration().build()

        repository = TodoRepositoryImpl(db.dao)
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