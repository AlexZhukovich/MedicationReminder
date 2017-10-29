package com.alexzh.medicationreminder.data.local

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

class LocalPillsPillsRepository : PillsRepository {
    override fun getMorePills(): Single<List<Pill>> {
        return Single.just(listOf())
    }
}