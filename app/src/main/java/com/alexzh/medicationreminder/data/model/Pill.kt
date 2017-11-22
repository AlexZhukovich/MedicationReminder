package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.Index
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.ForeignKey

@Entity(
        tableName = "pills",
        foreignKeys = arrayOf(
                ForeignKey(
                        entity = Reminder::class,
                        parentColumns = arrayOf("reminder_id"),
                        childColumns = arrayOf("reminder_id"),
                        onDelete = ForeignKey.CASCADE)
        ),
        indices = arrayOf(Index("pill_id"), Index("reminder_id"))
)
data class Pill(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "pill_id") val id: Long,
                @ColumnInfo(name = "pill_name") val name: String,
                @ColumnInfo(name = "pill_description") val description: String,
                @ColumnInfo(name = "pill_dosage") val dosage: String,
                @ColumnInfo(name = "reminder_id") val reminderId: Long)