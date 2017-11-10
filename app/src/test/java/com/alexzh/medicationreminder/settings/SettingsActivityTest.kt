@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.settings

import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class SettingsActivityTest {

    @Test
    fun `Verify newIntent instance from SettingsActivity`() {
        val context = RuntimeEnvironment.application
        val actualIntent = SettingsActivity.newInstance(context)

        assertEquals(actualIntent.component.className, SettingsActivity::class.java.name)
    }
}