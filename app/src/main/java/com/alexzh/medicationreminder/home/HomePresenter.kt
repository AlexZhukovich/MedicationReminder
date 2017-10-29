package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class HomePresenter(private val view: Home.View, private val pillsRepository: PillsRepository) : Home.Presenter {

    private var mDisposable: Disposable? = null

    override fun loadMore() {
        mDisposable = pillsRepository.getMorePills()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
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