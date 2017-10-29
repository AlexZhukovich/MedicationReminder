package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

interface PillsRepository {
    fun getMorePills() : Single<List<Pill>>
}