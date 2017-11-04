package com.alexzh.medicationreminder.pilldetail

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import org.junit.Rule
import org.junit.Test

class PillDetailActivityMockUITest {

    companion object {
        val NAVIGATE_UP_DESCRIPTION = "Navigate up"
    }

    @Rule @JvmField
    val mActivityRule = ActivityTestRule<PillDetailActivity>(PillDetailActivity::class.java)

    @Test
    fun shouldCheckToolbarForNewPill() {
        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))

        onView(withText(R.string.action_add_pill))
                .check(matches(isDisplayed()))
    }
}
