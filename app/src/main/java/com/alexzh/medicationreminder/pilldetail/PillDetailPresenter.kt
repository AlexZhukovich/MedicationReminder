package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.disposables.Disposable

class PillDetailPresenter(private val repository: PillsRepository,
                          private val view: PillDetail.View) : PillDetail.Presenter {

    private var mDisposable: Disposable? = null

    override fun loadPillInfo(pillId: Long) {
        mDisposable = repository.getPillById(pillId)
                .subscribe(this::handleSuccess, this::handleError)
    }

    override fun onDestroy() {
        mDisposable?.dispose()
    }

    private fun handleSuccess(pill: Pill) {
        view.showPillInfo(pill)
    }

    private fun handleError(t: Throwable) {
        view.showErrorMessage()
    }
}