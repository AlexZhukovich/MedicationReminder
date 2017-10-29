package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.disposables.Disposable

class HomePresenter(private val view: Home.View, private val pillsRepository: PillsRepository) : Home.Presenter {

    private var mDisposable: Disposable? = null

    override fun loadMore() {
        mDisposable = pillsRepository.getMorePills()
                .doOnSubscribe { view.showLoader() }
                .doFinally { view.hideLoader() }
                .subscribe(this::handleSuccess, this::handleError)
    }

    private fun handleSuccess(pills: List<Pill>) {
        view.showPills(pills)
    }

    private fun handleError(t: Throwable) {
        view.showLoadingError()
    }

    override fun onDestroy() {
        mDisposable?.dispose()
    }
}