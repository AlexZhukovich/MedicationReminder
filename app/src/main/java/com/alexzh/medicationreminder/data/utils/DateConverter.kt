package com.alexzh.medicationreminder.data.utils

import android.arch.persistence.room.TypeConverter
import java.util.Date

class DateConverter {
    @TypeConverter
    fun fromTimeStamp(value: Long) : Date = Date(value)

    @TypeConverter
    fun fromDate(value: Date) : Long = value.time
}