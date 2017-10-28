package com.alexzh.medicationreminder.data

import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.home.Home
import io.reactivex.Single

class PillsRepository : Home.Repository {
    override fun getMorePills(): Single<List<Pill>> {
        return Single.just(listOf())
    }
}