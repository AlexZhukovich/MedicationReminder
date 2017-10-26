@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import android.view.View
import com.alexzh.medicationreminder.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    @Test
    fun `HomeActivity contains the RecyclerView`() {
        val activity = Robolectric.setupActivity(HomeActivity::class.java)
        val recyclerView = activity.findViewById<View>(R.id.recyclerView)

        assertEquals(recyclerView.visibility, View.VISIBLE)
    }
}