package com.alexzh.medicationreminder.home

class HomePresenter(private val view: Home.View) : Home.Presenter {

    override fun loadMore() {
        view.showLoader()
    }
}