@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import android.content.Intent
import android.support.v7.widget.Toolbar
import android.view.View
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import org.junit.Assert.assertTrue
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    companion object {
        val DEFAULT_LONG_VALUE = -1L
    }

    @Test
    fun `HomeActivity contains the RecyclerView`() {
        val activity = Robolectric.setupActivity(HomeActivity::class.java)
        val recyclerView = activity.findViewById<View>(R.id.recyclerView)

        assertEquals(recyclerView.visibility, View.VISIBLE)
    }

    @Test
    fun `HomeActivity contains the Settings menu item`() {
        val activity = Robolectric.setupActivity(HomeActivity::class.java)
        val toolbar = activity.findViewById<View>(R.id.toolbar) as Toolbar
        val shadowActivity = shadowOf(activity)
        shadowActivity.onCreateOptionsMenu(toolbar.menu)

        assertTrue(shadowActivity.optionsMenu.hasVisibleItems())
    }

    @Test
    fun `Validate newIntent instance from PillDetailActivity`() {
        val activity = Robolectric.buildActivity(HomeActivity::class.java).create().get()

        val expectedIntent = Intent(activity, PillDetailActivity::class.java)
        expectedIntent.putExtra(PillDetailActivity.PILL_ID_KEY, TestData.getFirstPill().id)

        val actualIntent = PillDetailActivity.newIntent(activity, TestData.getFirstPill().id)

        assertEquals(expectedIntent.getLongExtra(PillDetailActivity.PILL_ID_KEY, DEFAULT_LONG_VALUE),
                     actualIntent.getLongExtra(PillDetailActivity.PILL_ID_KEY, DEFAULT_LONG_VALUE))
    }
}