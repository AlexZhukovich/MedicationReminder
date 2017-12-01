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
     * Save a new pill. If the pill already exists, replace it.
     *
     * @param pill the pill to be saved.
     * @return the Completable for saving the pill.
     */
    override fun savePill(pill: Pill): Completable {
        return Completable.fromAction({
            pillDao.insert(pill)
        })
    }

    /**
     * Save a list of pill. If the pills already exist, replace them.
     *
     * @param pills the list of pills to be saved.
     * @return the Completable for saving the list of pills.
     */
    override fun savePills(pills: List<Pill>) : Completable {
        return Completable.fromAction({
            pillDao.insert(pills)
        })
    }

    /**
     * Update an existing pill.
     *
     * @param pill the pill to be updated.
     * @return the completable for updating pill.
     */
    override fun updatePill(pill: Pill) : Completable {
        return Completable.fromAction({
            pillDao.update(pill)
        })
    }

    /**
     * Update an existing reminder.
     *
     * @param reminder to be updated.
     * @return the Completable for updating a reminder.
     */
    override fun updateReminder(reminder: Reminder): Completable {
        return Completable.fromAction({
            reminderDao.update(reminder)
        })
    }

    /**
     * Remove an existing pill by pill ID.
     *
     * @param pill the pill to be removed.
     * @return the Completable for removing a pill.
     */
    override fun removePill(pill: Pill): Completable {
        return Completable.fromAction({
            pillDao.delete(pill)
        })
    }

    /**
     * Remove an existing reminder by reminder ID.
     *
     * @param reminder the reminder to be removed.
     * @return the Completable for removing a reminder.
     */
    override fun removeReminder(reminder: Reminder): Completable {
        return Completable.fromAction({
            reminderDao.delete(reminder)
        })
    }
}