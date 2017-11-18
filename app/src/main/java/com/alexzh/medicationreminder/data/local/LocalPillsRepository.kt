package com.alexzh.medicationreminder.data.local

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

/**
 * Access Point for managing pills data.
 */
class LocalPillsRepository(private val pillDao: PillDao) : PillsRepository {
    /**
     * Get pills from the database.
     *
     * @return the users from the database.
     */
    override fun getPills(): Single<List<Pill>> = pillDao.getPills()

    /**
     * Get pill by ID from the database.
     *
     * @param id the id of the pill.
     * @return the pill from the database.
     */
    override fun getPillById(id: Long): Single<Pill> = pillDao.getPillById(id)

    /**
     * Insert a new pill. If the pill already exists, replace it.
     *
     * @param pill the pill to be inserted.
     */
    override fun insertPill(pill: Pill) = pillDao.insertPill(pill)

    /**
     * Update an existing pill.
     *
     * @param pill the pill to be updated.
     */
    override fun updatePill(pill: Pill) = pillDao.updatePill(pill)
}