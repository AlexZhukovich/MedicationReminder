package com.alexzh.medicationreminder.data.local

import android.content.Context
import com.alexzh.medicationreminder.data.AppInfoRepository
import io.reactivex.Single

class LocalAppInfoRepository(private val context: Context) : AppInfoRepository {
    override fun getAppVersion(): Single<String> {
        return Single.just(context.packageManager.getPackageInfo(context.packageName, 0).versionName)
    }
}