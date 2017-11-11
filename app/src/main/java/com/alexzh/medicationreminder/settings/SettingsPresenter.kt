package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository

class SettingsPresenter(private val view: Settings.View,
                        private val repository: AppInfoRepository) : Settings.Presenter {

    override fun loadAppVersion() {
        repository.getAppVersion()
                .subscribe({view.showAppVersion(it)}, {})
    }
}