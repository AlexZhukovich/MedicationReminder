package com.alexzh.medicationreminder.app

import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.RecyclerViewItemCountAssertion
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.home.HomeActivity
import org.junit.Rule
import org.junit.Test

class AppE2ETest {

    companion object {
        private val NAVIGATE_UP_DESCRIPTION = "Navigate up"
        private val FIRST_ITEM = 0
    }

    @Rule @JvmField
    val activityRule = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            super.beforeActivityLaunched()
            MedicationReminderDatabase.getInstance(InstrumentationRegistry.getTargetContext())
                    .pillDao()
                    .deleteAllPills()
        }
    }

    @Test
    fun shouldInsertNewPillAndBackAfterOpeningExistingPill() {
        onView(withId(R.id.recyclerView))
                .check(RecyclerViewItemCountAssertion(0))

        onView(withId(R.id.add))
                .perform(click())

        onView(withId(R.id.pillName))
                .perform(replaceText(TestData.getFirstPill().name))

        onView(withId(R.id.pillDosage))
                .perform(replaceText(TestData.getFirstPill().dosage))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .perform(click())

        onView(withId(R.id.recyclerView))
                .check(RecyclerViewItemCountAssertion(1))
                .perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(FIRST_ITEM, click()))

        onView(withId(R.id.pillName))
                .check(matches(withText(TestData.getFirstPill().name)))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .perform(click())

        onView(withId(R.id.recyclerView))
                .check(RecyclerViewItemCountAssertion(1))
    }
}
