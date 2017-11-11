package com.alexzh.medicationreminder.settings

interface Settings {
    interface Presenter {
        fun loadAppVersion()
    }
    interface View {
        fun showAppVersion(version: String)
        fun showUnknownAppVersion()
    }
}