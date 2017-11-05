package com.alexzh.medicationreminder.pilldetail

interface PillDetail {
    interface Presenter {
        fun loadPillInfo(pillId : Long)
    }
}