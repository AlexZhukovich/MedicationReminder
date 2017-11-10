package com.alexzh.medicationreminder.settings

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import org.junit.Rule
import org.junit.Test

class SettingsActivityMockUITest {

    @Rule @JvmField
    val mActivityRule = ActivityTestRule<SettingsActivity>(SettingsActivity::class.java)

    @Test
    fun shouldDisplayAboutCategory() {
        onView(withText(R.string.pref_title_category_about))
                .check(ViewAssertions.matches(isDisplayed()))
    }
}