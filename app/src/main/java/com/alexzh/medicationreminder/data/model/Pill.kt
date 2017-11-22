package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "pills")
data class Pill(@PrimaryKey(autoGenerate = true) @ColumnInfo(name = "pill_id") val id: Long,
                @ColumnInfo(name = "pill_name") val name: String,
                @ColumnInfo(name = "pill_description") val description: String,
                @ColumnInfo(name = "pill_dosage") val dosage: String)