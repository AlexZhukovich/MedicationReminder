package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository

class PillDetailPresenter(private val repository: PillsRepository) : PillDetail.Presenter {

    override fun loadPillInfo(pillId: Long) {
        repository.getPillById(pillId)
    }
}