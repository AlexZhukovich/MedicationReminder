package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

interface Repository {
    fun getMorePills() : Single<List<Pill>>
}