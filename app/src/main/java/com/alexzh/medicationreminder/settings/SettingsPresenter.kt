package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository
import timber.log.Timber

class SettingsPresenter(private val view: Settings.View,
                        private val repository: AppInfoRepository) : Settings.Presenter {

    override fun loadAppVersion() {
        repository.getAppVersion()
                .subscribe(this::handleSuccess, this::handleError)
    }

    private fun handleSuccess(version: String) {
        view.showAppVersion(version)
    }

    private fun handleError(t: Throwable) {
        Timber.e(t, "There was an error loading the app version.")
        view.showUnknownAppVersion()
    }
}
