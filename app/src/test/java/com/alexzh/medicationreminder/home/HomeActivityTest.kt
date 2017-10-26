@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import android.view.View
import com.alexzh.medicationreminder.MainActivity
import com.alexzh.medicationreminder.R
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.Robolectric
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class HomeActivityTest {

    @Test
    fun `Activity was created`() {
        val activity = Robolectric.setupActivity(MainActivity::class.java)
        val recyclerView = activity.findViewById<View>(R.id.recyclerView)

        assertEquals(recyclerView.visibility, View.VISIBLE)
    }
}