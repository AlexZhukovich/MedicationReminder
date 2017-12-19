package com.alexzh.medicationreminder.settings

import android.content.pm.PackageManager
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.AppInfoRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.hamcrest.CoreMatchers.not
import org.hamcrest.CoreMatchers.startsWith
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class SettingsActivityMockUITest {

    companion object {
        private val NAVIGATE_UP_DESCRIPTION = "Navigate up"
        val APP_VERSION = "0.0.1-dev"
    }

    private val mRepository = mock<AppInfoRepository>()
    private val mAppInfoSubject = SingleSubject.create<String>()

    @Rule @JvmField
    var thrown = ExpectedException.none()

    @Rule @JvmField
    val mActivityRule = object: ActivityTestRule<SettingsActivity>(SettingsActivity::class.java) {
        override fun beforeActivityLaunched() {
            SettingsFragment.mRepository = mRepository

            whenever(mRepository.getAppVersion()).thenReturn(mAppInfoSubject)
        }
    }

    @Test
    fun shouldDisplayAboutCategoryWithVersionPreference() {
        onView(withText(R.string.pref_title_category_about))
                .check(matches(isDisplayed()))

        onView(withText(R.string.pref_title_app_version))
                .check(matches(isDisplayed()))

        mAppInfoSubject.onSuccess(APP_VERSION)

        onView(withText(APP_VERSION))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayAboutCategoryWithUnknownAppVersionPreference() {
        onView(withText(R.string.pref_title_category_about))
                .check(matches(isDisplayed()))

        onView(withText(R.string.pref_title_app_version))
                .check(matches(isDisplayed()))

        mAppInfoSubject.onError(PackageManager.NameNotFoundException())

        onView(withText(R.string.pref_summary_app_version_unknown))
                .check(matches(isDisplayed()))
    }

    /**
     * Test wait for exception, because Activity should be finished.
     */
    @Test
    fun shouldCheckNavigationUpButton() {
        // Preparing expected exception.
        thrown.expect(RuntimeException::class.java)
        thrown.expectMessage(startsWith("No activities found."))

        onView(withText(R.string.pref_title_category_about))
                .check(matches(isDisplayed()))

        onView(ViewMatchers.withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))
                .perform(ViewActions.click())

        onView(withText(R.string.pref_title_category_about))
                .check(matches(not(isDisplayed())))
    }
}