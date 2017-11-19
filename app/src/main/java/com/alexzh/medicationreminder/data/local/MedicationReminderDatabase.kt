package com.alexzh.medicationreminder.data.local

import android.arch.persistence.room.Room
import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import android.arch.persistence.room.TypeConverters
import com.alexzh.medicationreminder.data.model.Pill
import android.content.Context
import com.alexzh.medicationreminder.data.model.Reminder
import com.alexzh.medicationreminder.data.utils.DateConverter

@Database(entities = arrayOf(Pill::class, Reminder::class), version = 1, exportSchema = false)
@TypeConverters(DateConverter::class)
abstract class MedicationReminderDatabase : RoomDatabase() {

    companion object {
        private val DATABASE_NAME = "medication-reminder.db"

        @Volatile private var mInstance: MedicationReminderDatabase? = null

        fun getInstance(context: Context): MedicationReminderDatabase {
            if (mInstance == null) {
                synchronized(MedicationReminderDatabase::class.java) {
                    if (mInstance == null) {
                        mInstance = Room.databaseBuilder(
                                context.applicationContext,
                                MedicationReminderDatabase::class.java,
                                DATABASE_NAME)
                                .build()
                    }
                }
            }
            return mInstance!!
        }
    }

    abstract fun pillDao(): PillDao

    abstract fun reminderDao(): ReminderDao
}