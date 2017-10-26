@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import android.support.v7.widget.Toolbar
import android.view.View
import com.alexzh.medicationreminder.R
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner
import org.robolectric.Shadows.shadowOf

@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

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
}