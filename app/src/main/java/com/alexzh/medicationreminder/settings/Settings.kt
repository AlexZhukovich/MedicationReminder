package com.alexzh.medicationreminder.settings

interface Settings {
    interface Presenter {
        fun loadAppVersion()
        fun onDestroy()
    }
    interface View {
        fun showAppVersion(version: String)
        fun showUnknownAppVersion()
    }
}