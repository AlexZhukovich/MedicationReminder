package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class PillDetailPresenter(private val view: PillDetail.View,
                          private val repository: PillsRepository,
                          private val ioScheduler: Scheduler = Schedulers.io(),
                          private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()) : PillDetail.Presenter {

    private var mDisposable: Disposable? = null

    override fun loadPillInfo(pillId: Long) {
        mDisposable = repository.getPillById(pillId)
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::handleSuccess, this::handleError)
    }

    override fun savePill() {
        view.getPillName()
    }

    override fun onDestroy() {
        mDisposable?.dispose()
    }

    private fun handleSuccess(pill: Pill) {
        view.showPillInfo(pill)
    }

    private fun handleError(t: Throwable) {
        Timber.e(t, "There was an error loading the pill.")
        view.showErrorMessage()
        view.close()
    }
}