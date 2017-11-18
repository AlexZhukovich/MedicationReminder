package com.alexzh.medicationreminder.data.local

import android.content.Context
import com.alexzh.medicationreminder.data.AppInfoRepository
import io.reactivex.Single

/**
 * Local implementation of getting current version of the application.
 */
class LocalAppInfoRepository(private val context: Context) : AppInfoRepository {

    /**
     * Get version of the app.
     *
     * @return the version of the app.
     */
    override fun getAppVersion(): Single<String> {
        return Single.just(context.packageManager.getPackageInfo(context.packageName, 0).versionName)
    }
}