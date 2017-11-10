@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    companion object {
        val DEFAULT_LONG_VALUE = -1L
    }

    @Test
    fun `Validate newIntent instance from PillDetailActivity for the existing pill`() {
        val context = RuntimeEnvironment.application
        val actualIntent = PillDetailActivity.newIntent(context, TestData.getFirstPill().id)

        assertEquals(TestData.getFirstPill().id,
                     actualIntent.getLongExtra(PillDetailActivity.PILL_ID_KEY, DEFAULT_LONG_VALUE))
    }

    @Test
    fun `Validate newIntent instance from PillDetailActivity for a new pill`() {
        val context = RuntimeEnvironment.application
        val actualIntent = PillDetailActivity.newIntent(context)

        assertEquals(DEFAULT_LONG_VALUE,
                     actualIntent.getLongExtra(PillDetailActivity.PILL_ID_KEY, DEFAULT_LONG_VALUE))
    }
}