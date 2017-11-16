package com.alexzh.medicationreminder.data.local

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

@Dao
interface PillDao {

    @Query("SELECT * FROM pill")
    fun getPills() : Single<List<Pill>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertPill(firstPill: Pill)
}