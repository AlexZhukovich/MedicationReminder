package com.alexzh.medicationreminder

import android.app.Application
import com.alexzh.medicationreminder.data.local.LocalAppInfoRepository
import com.alexzh.medicationreminder.data.local.LocalPillsRepository
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.home.HomeActivity
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import com.alexzh.medicationreminder.settings.SettingsFragment
import timber.log.Timber
import com.alexzh.medicationreminder.data.PillsRepository

class MedicationReminderApp : Application() {

    private val mPillRepository: PillsRepository by lazy {
        LocalPillsRepository(
                MedicationReminderDatabase.getInstance(this).pillDao(),
                MedicationReminderDatabase.getInstance(this).reminderDao())
    }

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
        HomeActivity.mPillsRepository = mPillRepository
    }

    private fun setupPillDetailActivity() {
        PillDetailActivity.mPillsRepository = mPillRepository
    }

    private fun setupSettings() {
        SettingsFragment.mRepository = LocalAppInfoRepository(this)
    }
}