package com.alexzh.medicationreminder.data

import io.reactivex.Single

/**
 * Access Point for getting information about the application.
 */
interface AppInfoRepository {

    /**
     * Get version of the app.
     *
     * @return the version of the app.
     */
    fun getAppVersion() : Single<String>
}