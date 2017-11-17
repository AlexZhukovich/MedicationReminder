package com.alexzh.medicationreminder.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
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
}