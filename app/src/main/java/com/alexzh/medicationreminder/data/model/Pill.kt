package com.alexzh.medicationreminder.data.model

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "pill")
data class Pill(@PrimaryKey(autoGenerate = true) val id: Long,
                val name: String,
                val description: String,
                val dosage: String)