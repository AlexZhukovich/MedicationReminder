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
     * Get pill by id.
     *
     * @param id the id of the pill.
     * @return the pill.
     */
    fun getPillById(id: Long) : Single<Pill>

    /**
     * Insert a new pill.
     *
     * @param pill the pill to be inserted.
     * @return the completable of inserting pill with reminder.
     */
    fun insertPill(pill: Pill, reminder: Reminder) : Completable

    /**
     * Insert a list of pills.
     *
     * @param pills the list of pills to be inserted.
     * @return the completable for inserting list of pills with list of reminders.
     */
    fun insertPills(pills: List<Pill>, reminders: List<Reminder>) : Completable

    /**
     * Update an existing pill.
     *
     * @param pill the pill to be inserted.
     * @return the completable for updating pill.
     */
    fun updatePill(pill: Pill) : Completable

    /**
     * Delete an existing pill.
     *
     * @param pill the pill to be updated.
     * @return the completable for deleting pill.
     */
    fun deletePill(pill: Pill) : Completable

    /**
     * Delete all pills.
     */
    fun deleteAllPills()
}