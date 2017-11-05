package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.model.Pill

interface PillDetail {
    interface Presenter {
        fun loadPillInfo(pillId : Long)
        fun onDestroy()
    }

    interface View {
        fun showPillInfo(pill: Pill)
        fun showErrorMessage()
    }
}