package com.alexzh.medicationreminder.home

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.RecyclerViewItemCountAssertion
import com.alexzh.medicationreminder.TestData
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

            whenever(mRepository.getPills()).thenReturn(mPillsSubject)
        }
    }

    @Test
    fun shouldDisplayTestPill() {
        mPillsSubject.onSuccess(TestData.getPills())

        onView(withId(R.id.recyclerView))
                .check(RecyclerViewItemCountAssertion(TestData.getPills().size))
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
        mPillsSubject.onSuccess(TestData.getPills())

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldNotDisplayProgressBarAfterLoadingError() {
        mPillsSubject.onError(RuntimeException())

        onView(withId(R.id.progressBar))
                .check(matches(not(isDisplayed())))
    }

    @Test
    fun shouldDisplayAddButton() {
        onView(withId(R.id.add))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplaySettingsMenuItem() {
        openContextualActionModeOverflowMenu()

        onView(withText(R.string.action_settings))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldCheckPillItem() {
        val pill = TestData.getFirstPill()
        mPillsSubject.onSuccess(listOf(pill))

        onView(withId(R.id.pillTitle))
                .check(matches(withText(pill.name)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.pillDosage))
                .check(matches(withText(pill.dosage)))
                .check(matches(isDisplayed()))
    }
}