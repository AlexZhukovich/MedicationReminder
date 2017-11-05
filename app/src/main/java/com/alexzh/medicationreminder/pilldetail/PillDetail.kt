package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.model.Pill

interface PillDetail {
    interface Presenter {
        fun loadPillInfo(pillId : Long)
    }

    interface View {
        fun showPillInfo(pill: Pill)
    }
}