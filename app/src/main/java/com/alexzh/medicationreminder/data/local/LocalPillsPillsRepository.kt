package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

class PillsRepository : Repository {
    override fun getMorePills(): Single<List<Pill>> {
        return Single.just(listOf())
    }
}