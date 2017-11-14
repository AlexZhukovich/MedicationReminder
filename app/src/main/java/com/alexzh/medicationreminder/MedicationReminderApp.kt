package com.alexzh.medicationreminder

import android.app.Application
import com.alexzh.medicationreminder.data.local.LocalAppInfoRepository
import com.alexzh.medicationreminder.data.local.LocalPillsRepository
import com.alexzh.medicationreminder.home.HomeActivity
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import com.alexzh.medicationreminder.settings.SettingsFragment
import timber.log.Timber

class MedicationReminderApp : Application() {

    override fun onCreate() {
        super.onCreate()
        setupTimber()
        setupHomeActivity()
        setupPillDetailActivity()
        setupSettings()
    }

    private fun setupTimber() {
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }

    private fun setupHomeActivity() {
        HomeActivity.mPillsRepository = LocalPillsRepository()
    }

    private fun setupPillDetailActivity() {
        PillDetailActivity.mPillsRepository = LocalPillsRepository()
    }

    private fun setupSettings() {
        SettingsFragment.mRepository = LocalAppInfoRepository(this)
    }
}