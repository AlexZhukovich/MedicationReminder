package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository

class SettingsPresenter(private val repository: AppInfoRepository) : Settings.Presenter {

    override fun loadAppVersion() {
        repository.getAppVersion()
    }
}