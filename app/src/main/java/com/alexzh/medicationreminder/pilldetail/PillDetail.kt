package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.model.Pill

interface PillDetail {
    interface Presenter {
        fun loadPillInfo(pillId : Long)
        fun savePill()
        fun onDestroy()
    }

    interface View {
        fun showPillInfo(pill: Pill)
        fun showErrorMessage()
        fun getPillName() : String
        fun getPillDescription() : String
        fun close()
    }
}