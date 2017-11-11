package com.alexzh.medicationreminder.settings

import android.content.pm.PackageManager
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.AppInfoRepository
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test

class SettingsActivityMockUITest {

    companion object {
        val APP_VERSION = "0.0.1-dev"
    }

    private val mRepository = mock<AppInfoRepository>()
    private val mAppInfoSubject = SingleSubject.create<String>()

    @Rule @JvmField
    val mActivityRule = object: ActivityTestRule<SettingsActivity>(SettingsActivity::class.java) {
        override fun beforeActivityLaunched() {
            SettingsFragment.mRepository = mRepository

            whenever(mRepository.getAppVersion()).thenReturn(mAppInfoSubject)
        }
    }

    @Test
    fun shouldDisplayAboutCategory() {
        onView(withText(R.string.pref_title_category_about))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayAppVersionPreference() {
        onView(withText(R.string.pref_title_app_version))
                .check(matches(isDisplayed()))

        mAppInfoSubject.onSuccess(APP_VERSION)

        onView(withText(APP_VERSION))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayUnknownAppVersionPreference() {
        onView(withText(R.string.pref_title_app_version))
                .check(matches(isDisplayed()))

        mAppInfoSubject.onError(PackageManager.NameNotFoundException())

        onView(withText(R.string.pref_summary_app_version_unknown))
                .check(matches(isDisplayed()))
    }
}