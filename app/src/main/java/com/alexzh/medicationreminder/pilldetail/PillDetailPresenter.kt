package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill

class PillDetailPresenter(private val repository: PillsRepository,
                          private val view: PillDetail.View) : PillDetail.Presenter {

    override fun loadPillInfo(pillId: Long) {
        repository.getPillById(pillId)
                .subscribe(this::handleSuccess, this::handleError)
    }

    private fun handleSuccess(pill: Pill) {
        view.showPillInfo(pill)
    }

    private fun handleError(t: Throwable) {
        view.showErrorMessage()
    }
}