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
     * Get all reminder for the pill.
     *
     * @param pillId the ID of the pill for looking all reminders.
     * @return the list of reminders for the pill.
     */
    @Query("SELECT * FROM reminders WHERE pill_id = :pillId")
    fun getRemindersByPillId(pillId: Long) : List<Reminder>

    /**
     * Insert a reminder in the database. If the reminder already exists, replace it.
     *
     * @param reminder the reminder to be inserted.
     * @return the reminder ID.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminder: Reminder) : Long

    /**
     * Insert a reminder in the database. All reminders inserted in one transaction.
     * If the reminder already exists, replace it.
     *
     * @param reminders the list of reminders to be inserted.
     * @return the list of reminders ID.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(reminders: List<Reminder>) : List<Long>

    /**
     * Update a reminder in the database.
     *
     * @param reminder the reminder to be updated.
     * @return the number of updated rows.
     */
    @Update
    fun update(reminder: Reminder) : Int

    /**
     * Delete a reminder from the database.
     *
     * @param reminder the reminder to be deleted.
     * @return the number of deleted rows.
     */
    @Delete
    fun delete(reminder: Reminder) : Int

    /**
     * Delete list of reminders from the table.
     *
     * @param reminders the list of reminders to be deleted.
     * @return the number of deleted rows.
     */
    @Delete
    fun delete(reminders: List<Reminder>) : Int

    /**
     * Delete all reminders from the database.
     */
    @Query("DELETE FROM reminders")
    fun deleteAll()
}