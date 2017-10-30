package com.alexzh.medicationreminder.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.*
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.RecyclerViewItemCountAssertion
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.hamcrest.Matchers.not
import org.junit.Rule
import org.junit.Test

class HomeActivityMockUITest {

    private val mRepository = mock<PillsRepository>()
    private val mPillsSubject = SingleSubject.create<List<Pill>>()

    @Rule @JvmField
    val mActivityRule = object: ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            HomeActivity.mPillsRepository = mRepository

            whenever(mRepository.getMorePills()).thenReturn(mPillsSubject)
        }
    }

    @Test
    fun shouldDisplayTestPill() {
        mPillsSubject.onSuccess(listOf(Pill("title", "description")))

        onView(withId(R.id.recyclerView))
                .check(RecyclerViewItemCountAssertion(1))
    }

    @Test
    fun shouldDisplayLoadingError() {
        mPillsSubject.onError(RuntimeException())

        onView(withId(android.support.design.R.id.snackbar_text))
                .check(matches(withText(R.string.message_load_data_error)))
    }

    @Test
    fun shouldDisplayProgressBarDuringLoadingData() {
        onView(withId(R.id.progressBar))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldNotDisplayProgressBarAfterSuccessLoading() {
        mPillsSubject.onSuccess(listOf(Pill("title", "description")))

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())))
    }
}