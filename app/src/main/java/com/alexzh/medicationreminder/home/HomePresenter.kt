package com.alexzh.medicationreminder.home

class HomePresenter(private val view: Home.View, private val repository: Home.Repository) : Home.Presenter {

    override fun loadMore() {
        view.showLoader()
        repository.getMorePills()
        view.hideLoader()
    }
}