@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.settings

import com.alexzh.medicationreminder.data.AppInfoRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class SettingsPresenterTest {

    private val repository = mock<AppInfoRepository>()
    private val presenter = SettingsPresenter(repository)

    @Test
    fun `Call repository during loading app version`() {
        presenter.loadAppVersion()

        verify(repository).getAppVersion()
    }
}