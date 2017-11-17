package com.alexzh.medicationreminder.data.local

import android.arch.persistence.room.*
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

/**
 * Data Access Object for the pills table.
 */
@Dao
interface PillDao {

    /**
     * Get all pills from the table.
     *
     * @return the pills.
     */
    @Query("SELECT * FROM pills")
    fun getPills() : Single<List<Pill>>

    /**
     * Insert a pill in the database. If the pill already exists, replace it.
     *
     * @param pill the pill to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPill(pill: Pill)

    /**
     * Insert list of pills in the database. All pills inserted in one transaction.
     * If the pill already exists, replace it.
     *
     * @param pills the list of pills to be inserted.
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPills(pills: List<Pill>)

    /**
     * Update an existing pill in the database.
     *
     * @param pill the pill to be updated.
     */
    @Update
    fun updatePill(pill: Pill)

    /**
     * Delete an existing pill from the database.
     *
     * @param pill the pill to be removed.
     */
    @Delete
    fun deletePill(pill: Pill)

    /**
     * Delete all pills from the database.
     */
    @Query("DELETE FROM pills")
    fun deleteAllPills()
}