package com.alexzh.medicationreminder

import android.app.Application
import com.alexzh.medicationreminder.data.local.LocalPillsPillsRepository
import com.alexzh.medicationreminder.home.HomeActivity
import timber.log.Timber

class MedicationReminderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupHomeActivity()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupHomeActivity() {
        HomeActivity.mPillsRepository = LocalPillsPillsRepository()
    }
}