package com.alexzh.medicationreminder.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Query
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Update
import android.arch.persistence.room.Delete
import android.arch.persistence.room.OnConflictStrategy
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

    /**
     * Insert a reminder in the database. If the reminder already exists, replace it.
     *
     * @param reminder the reminder to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminder: Reminder) : Long

    /**
     * Insert a reminder in the database. All reminders inserted in one transaction.
     * If the reminder already exists, replace it.
     *
     * @param reminders the list of reminders to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: List<Reminder>)

    /**
     * Update a reminder in the database.
     *
     * @param reminder the reminder to be updated.
     */
    @Update
    fun update(reminder: Reminder)

    /**
     * Delete a reminder from the database.
     *
     * @param reminder the reminder to be deleted.
     */
    @Delete
    fun delete(reminder: Reminder)

    /**
     * Delete list of reminders from the table.
     *
     * @param reminders the list of reminders to be deleted.
     */
    @Delete
    fun delete(reminders: List<Reminder>)

    /**
     * Delete all reminders from the database.
     */
    @Query("DELETE FROM reminders")
    fun deleteAll()
}