package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.Index
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.ColumnInfo

@Entity(tableName = "pills",
        indices = [(Index("id"))]
)
data class Pill(@ColumnInfo(name = "pill_name") val name: String,
                @ColumnInfo(name = "pill_description") val description: String,
                @ColumnInfo(name = "pill_dosage") val dosage: String) {

    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = "id") var id: Long = 0
}