package com.alexzh.medicationreminder.home

interface Home {
    interface View {
        fun showLoader()
        fun hideLoader()
    }

    interface Presenter {
        fun loadMore()
    }
}