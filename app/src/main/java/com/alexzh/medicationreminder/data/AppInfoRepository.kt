package com.alexzh.medicationreminder.data

import io.reactivex.Single

interface AppInfoRepository {
    fun getAppVersion() : Single<String>
}