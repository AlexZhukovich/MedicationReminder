package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.data.model.Pill

interface Home {
    interface View {
        fun showLoader()
        fun hideLoader()
        fun showPills(pills: List<Pill>)
        fun showLoadingError()
    }

    interface Presenter {
        fun loadMore()
        fun onDestroy()
    }
}