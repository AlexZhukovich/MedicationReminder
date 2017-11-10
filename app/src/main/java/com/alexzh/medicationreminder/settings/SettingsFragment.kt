package com.alexzh.medicationreminder.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.alexzh.medicationreminder.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_prefs)
    }
}