package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.model.Pill

interface PillDetail {
    interface Presenter {
        fun loadPillInfo(pillId : Long)
        fun savePill()
        fun updatePill(pillId: Long)
        fun onDestroy()
    }

    interface View {
        fun showPillInfo(pill: Pill)
        fun showErrorMessage()
        fun getPillName() : String
        fun getPillDescription() : String
        fun getPillDosage() : String
        fun close()
    }
}