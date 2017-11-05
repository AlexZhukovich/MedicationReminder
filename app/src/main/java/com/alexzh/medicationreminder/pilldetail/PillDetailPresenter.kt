package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository

class PillDetailPresenter(private val repository: PillsRepository,
                          private val view: PillDetail.View) : PillDetail.Presenter {

    override fun loadPillInfo(pillId: Long) {
        repository.getPillById(pillId)
                .subscribe(
                        { view.showPillInfo(it) },
                        {})
    }
}