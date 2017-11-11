package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber

class SettingsPresenter(private val view: Settings.View,
                        private val repository: AppInfoRepository,
                        private val ioScheduler: Scheduler = Schedulers.io(),
                        private val uiScheduler: Scheduler = AndroidSchedulers.mainThread()) : Settings.Presenter {

    private var mDisposable: Disposable? = null

    override fun loadAppVersion() {
        mDisposable = repository.getAppVersion()
                .subscribeOn(ioScheduler)
                .observeOn(uiScheduler)
                .subscribe(this::handleSuccess, this::handleError)
    }

    override fun onDestroy() {
        mDisposable?.dispose()
    }

    private fun handleSuccess(version: String) {
        view.showAppVersion(version)
    }

    private fun handleError(t: Throwable) {
        Timber.e(t, "There was an error loading the app version.")
        view.showUnknownAppVersion()
    }
}
