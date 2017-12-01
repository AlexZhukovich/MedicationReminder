package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.data.model.Reminder
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Access Point for managing pills data.
 */
interface PillsRepository {

    /**
     * Get all pills.
     *
     * @return the list of pills.
     */
    fun getPills() : Single<List<Pill>>

    /**
     * Get all reminders.
     *
     * @return the list of reminders.
     */
    fun getReminder(): Single<List<Reminder>>

    /**
     * Get pill by id.
     *
     * @param id the id of the pill.
     * @return the pill.
     */
    fun getPillById(id: Long) : Single<Pill>

    /**
     * Save a new pill.
     *
     * @param pill the pill to be saved.
     * @return the Completable for saving the pill.
     */
    fun savePill(pill: Pill) : Completable

    /**
     * Save a list of pill.
     *
     * @param pills the list of pills to be saved.
     * @return the Completable for saving the list of pills.
     */
    fun savePills(pills: List<Pill>) : Completable

    /**
     * Save a new reminder. If the reminder already exists, replace it.
     *
     * @param reminder the reminder to be saved.
     * @return the Completable for saving the reminder.
     */
    fun saveReminder(reminder: Reminder): Completable

    /**
     * Update an existing pill.
     *
     * @param pill the pill to be updated.
     * @return the Completable for updating a pill.
     */
    fun updatePill(pill: Pill) : Completable

    /**
     * Update an existing reminder.
     *
     * @param reminder to be updated.
     * @return the Completable for updating a reminder.
     */
    fun updateReminder(reminder: Reminder) : Completable

    /**
     * Remove an existing pill by pill ID.
     *
     * @param pill the pill to be removed.
     * @return the Completable for removing a pill.
     */
    fun removePill(pill: Pill) : Completable

    /**
     * Remove an existing reminder by reminder ID.
     *
     * @param reminder the reminder to be removed.
     * @return the Completable for removing a reminder.
     */
    fun removeReminder(reminder: Reminder) : Completable
}