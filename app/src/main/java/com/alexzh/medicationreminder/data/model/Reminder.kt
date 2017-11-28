package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.Index
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.PrimaryKey
import java.util.Date

@Entity(tableName = "reminders",
        foreignKeys = [(ForeignKey(
                entity = Pill::class,
                parentColumns = arrayOf("id"),
                childColumns = arrayOf("pill_id"),
                onDelete = ForeignKey.CASCADE))],
        indices = [(Index("pill_id"))]
)
data class Reminder(@ColumnInfo(name = "pill_id") val pillId: Long,
                    @ColumnInfo(name = "reminder start_date") val startDate: Date,
                    @ColumnInfo(name = "reminder end_date") val endDate: Date,
                    @ColumnInfo(name = "reminder_time") val reminderTime: Date) {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var reminderId: Long = 0
}