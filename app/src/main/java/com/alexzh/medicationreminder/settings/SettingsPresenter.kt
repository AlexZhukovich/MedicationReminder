package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository
import timber.log.Timber

class SettingsPresenter(private val view: Settings.View,
                        private val repository: AppInfoRepository) : Settings.Presenter {

    override fun loadAppVersion() {
        repository.getAppVersion()
                .subscribe(
                        {view.showAppVersion(it)},
                        {
                            Timber.e(it, "There was an error loading the app version.")
                            view.showUnknownAppVersion(it)
                        })
    }
}