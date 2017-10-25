package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.data.model.Pill

class HomePresenter(private val view: Home.View, private val repository: Home.Repository) : Home.Presenter {

    override fun loadMore() {
        view.showLoader()
        repository.getMorePills()
                .doOnError(this::handleError)
                .subscribe(this::handleSuccess)
        view.hideLoader()
    }

    private fun handleSuccess(pills: List<Pill>) {
        view.showPills(pills)
    }

    private fun handleError(t: Throwable) {
        view.showLoadingError()
    }
}