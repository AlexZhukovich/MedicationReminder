package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

interface PillsRepository {
    fun getPills() : Single<List<Pill>>
    fun getPillById(id: Long) : Single<Pill>
}