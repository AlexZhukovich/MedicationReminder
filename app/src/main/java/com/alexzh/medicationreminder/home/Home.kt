package com.alexzh.medicationreminder.home

interface Home {
    interface View {
        fun showLoader()
    }

    interface Presenter {
        fun loadMore()
    }
}