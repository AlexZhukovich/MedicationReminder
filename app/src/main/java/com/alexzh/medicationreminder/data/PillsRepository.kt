package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.data.model.Reminder
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
     */
    fun insertPill(pill: Pill, reminder: Reminder)

    /**
     * Insert a list of pills.
     *
     * @param pills the list of pills to be inserted.
     */
    fun insertPills(pills: List<Pill>, reminders: List<Reminder>)

    /**
     * Update an existing pill.
     *
     * @param pill the pill to be inserted.
     */
    fun updatePill(pill: Pill)

    /**
     * Delete an existing pill.
     *
     * @param pill the pill to be updated.
     */
    fun deletePill(pill: Pill)

    /**
     * Delete all pills.
     */
    fun deleteAllPills()
}