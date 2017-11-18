package com.alexzh.medicationreminder.data.local

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Single

class LocalPillsRepository(private val pillDao: PillDao) : PillsRepository {
    override fun getPills(): Single<List<Pill>> = pillDao.getPills()

    override fun getPillById(id: Long): Single<Pill> = pillDao.getPillById(id)
}