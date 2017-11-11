package com.alexzh.medicationreminder.settings

import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.AppInfoRepository

class SettingsFragment : PreferenceFragmentCompat(), Settings.View {

    companion object {
        lateinit var mRepository : AppInfoRepository
    }

    private val mPresenter: Settings.Presenter by lazy { SettingsPresenter(this, mRepository) }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        addPreferencesFromResource(R.xml.app_prefs)
        mPresenter.loadAppVersion()
    }

    override fun showUnknownAppVersion() {

    }

    override fun showAppVersion(version: String) {
        findPreference(getString(R.string.pref_key_app_version)).summary = version
    }
}