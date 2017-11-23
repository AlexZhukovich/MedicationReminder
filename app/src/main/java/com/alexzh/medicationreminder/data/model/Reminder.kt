package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.Index
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reminders",
        indices = arrayOf(Index("reminder_id")))
data class Reminder(@ColumnInfo(name = "reminder start_date") val startDate: Date,
                    @ColumnInfo(name = "reminder end_date") val endDate: Date,
                    @ColumnInfo(name = "reminder_time") val reminderTime: Date) {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "reminder_id") var reminderId: Long = 0
}