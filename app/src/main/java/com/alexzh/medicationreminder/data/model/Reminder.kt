package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reminders")
data class Reminder(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "reminder_id") val reminderId: Long,
                    @ColumnInfo(name = "start_date") val startDate: Date,
                    @ColumnInfo(name = "end_date") val endDate: Date,
                    @ColumnInfo(name = "reminder_time") val reminderTime: Date)