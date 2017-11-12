package com.alexzh.medicationreminder.data.local

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

class LocalPillsPillsRepository : PillsRepository {
    override fun getPillById(id: Long): Single<Pill> {
        return Single.just(Pill(1L, "Test pill", "Test description", "100 mg"))
    }

    override fun getMorePills(): Single<List<Pill>> {
        return Single.just(listOf())
    }
}