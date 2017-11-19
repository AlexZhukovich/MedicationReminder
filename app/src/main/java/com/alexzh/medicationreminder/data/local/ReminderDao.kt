package com.alexzh.medicationreminder.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import com.alexzh.medicationreminder.data.model.Reminder
import io.reactivex.Single

@Dao
interface ReminderDao {

    /**
     * Get all reminders.
     *
     * @return the reminders.
     */
    @Query("SELECT * FROM reminders")
    fun getReminders() : Single<List<Reminder>>
}