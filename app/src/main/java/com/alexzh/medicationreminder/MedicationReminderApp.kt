package com.alexzh.medicationreminder

import android.app.Application
import com.alexzh.medicationreminder.data.local.LocalPillsPillsRepository
import com.alexzh.medicationreminder.home.HomeActivity

class MedicationReminderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupHomeActivity()
    }

    private fun setupHomeActivity() {
        HomeActivity.mPillsRepository = LocalPillsPillsRepository()
    }
}