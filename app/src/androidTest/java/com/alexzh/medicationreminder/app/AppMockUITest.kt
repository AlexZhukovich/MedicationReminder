package com.alexzh.medicationreminder.app

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.home.HomeActivity
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test

class AppMockUITest {

    private val mRepository = mock<PillsRepository>()
    private val mPillsSubject = SingleSubject.create<List<Pill>>()

    @Rule
    @JvmField
    val mActivityRule = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            HomeActivity.mPillsRepository = mRepository

            whenever(mRepository.getMorePills()).thenReturn(mPillsSubject)
        }
    }

    @Test
    fun shouldClickToAddButtonAndCheckEmptyPillName() {
        onView(withId(R.id.add))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(withId(R.id.pillName))
                .check(matches(withText("")))
    }
}