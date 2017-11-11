@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Test

class SettingsPresenterTest {

    companion object {
        val APP_VERSION = "0.0.1-dev"
    }

    private val appInfoSubject = SingleSubject.create<String>()

    private val view = mock<Settings.View>()
    private val repository = mock<AppInfoRepository>().apply {
        whenever(this.getAppVersion()).thenReturn(appInfoSubject)
    }
    private val presenter = SettingsPresenter(view, repository)

    @Test
    fun `Call repository during loading app version`() {
        presenter.loadAppVersion()

        verify(repository).getAppVersion()
    }

    @Test
    fun `Display app version after success loading`() {
        presenter.loadAppVersion()

        appInfoSubject.onSuccess(APP_VERSION)

        verify(view).showAppVersion(APP_VERSION)
    }

    @Test
    fun `Display Unknown app version after loading error`() {
        presenter.loadAppVersion()

        appInfoSubject.onError(RuntimeException())

        verify(view).showUnknownAppVersion(any())
    }
}