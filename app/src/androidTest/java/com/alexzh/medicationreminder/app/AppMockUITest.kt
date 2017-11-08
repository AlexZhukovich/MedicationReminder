package com.alexzh.medicationreminder.app

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.v7.widget.RecyclerView
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.home.HomeActivity
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test

class AppMockUITest {

    companion object {
        private val NAVIGATE_UP_DESCRIPTION = "Navigate up"
        private val FIRST_ITEM = 0
    }

    private val mRepository = mock<PillsRepository>()
    private val mPillsSubject = SingleSubject.create<List<Pill>>()
    private val mPillSubject = SingleSubject.create<Pill>()

    @Rule
    @JvmField
    val mActivityRule = object : ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            HomeActivity.mPillsRepository = mRepository
            PillDetailActivity.mPillsRepository = mRepository

            whenever(mRepository.getMorePills()).thenReturn(mPillsSubject)
            whenever(mRepository.getPillById(any())).thenReturn(mPillSubject)
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

    @Test
    fun shouldCheckNavigateUpButton() {
        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))

        onView(withId(R.id.add))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(withId(R.id.pillName))
                .check(matches(withText("")))

        onView(ViewMatchers.withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))
                .perform(click())

        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldClickToTheFirstPillAndCheckDetailActivity() {
        mPillsSubject.onSuccess(TestData.getPills())
        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(FIRST_ITEM, click()))

        mPillSubject.onSuccess(TestData.getFirstPill())
        onView(withId(R.id.pillName))
                .check(matches(withText(TestData.getFirstPill().name)))
    }

    @Test
    fun shouldClickToTheFirstPillAndCloseDetailActivityAfterLoadingError() {
        mPillsSubject.onSuccess(TestData.getPills())
        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
                .perform(actionOnItemAtPosition<RecyclerView.ViewHolder>(FIRST_ITEM, click()))

        mPillSubject.onError(RuntimeException())

        onView(withId(R.id.recyclerView))
                .check(matches(isDisplayed()))
    }
}