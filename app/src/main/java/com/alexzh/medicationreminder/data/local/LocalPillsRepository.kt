package com.alexzh.medicationreminder.data.local

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.data.model.Reminder
import io.reactivex.Completable
import io.reactivex.Single

/**
 * Room implementation for managing pills data.
 */
class LocalPillsRepository(private val pillDao: PillDao, private val reminderDao: ReminderDao) : PillsRepository {
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
     * @return the completable of inserting pill with reminder.
     */
    override fun insertPill(pill: Pill, reminder: Reminder) : Completable {
        return Completable.fromAction({
            pillDao.insert(pill)
            reminderDao.insert(reminder)
        })
    }

    /**
     * Insert list of pills in the database. If the pill already exists, replace it.
     *
     * @param pills the list of pills.
     */
    override fun insertPills(pills: List<Pill>, reminders: List<Reminder>) {
        pillDao.insert(pills)
        reminderDao.insert(reminders)
    }

    /**
     * Update an existing pill.
     *
     * @param pill the pill to be updated.
     */
    override fun updatePill(pill: Pill) {
        pillDao.update(pill)
    }

    /**
     * Delete an existing pill.
     *
     * @param pill the pill to be deleted.
     */
    override fun deletePill(pill: Pill) {
        pillDao.delete(pill)
    }

    /**
     * Delete all pills.
     */
    override fun deleteAllPills() {
        reminderDao.deleteAll()
        pillDao.deleteAllPills()
    }
}