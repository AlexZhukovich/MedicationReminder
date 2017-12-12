package com.alexzh.medicationreminder.pilldetail

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.action.ViewActions.replaceText
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withHint
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withInputType
import android.support.test.rule.ActivityTestRule
import android.text.InputType
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test

class PillDetailActivityMockUITest {

    companion object {
        val NAVIGATE_UP_DESCRIPTION = "Navigate up"
        val EMPTY_TEXT = ""
    }

    private val mPillSubject = SingleSubject.create<Pill>()
    private val mRepository = mock<PillsRepository>().apply {
        whenever(this.getPillById(any())).thenReturn(mPillSubject)
        whenever(this.savePill(any())).thenReturn(Completable.complete())
        whenever(this.updatePill(any())).thenReturn(Completable.complete())
    }

    @Rule @JvmField
    val mActivityRule = ActivityTestRule<PillDetailActivity>(PillDetailActivity::class.java, true, false)

    @Test
    fun shouldDisplayMedicationNameAndDosageHints() {
        mActivityRule.launchActivity(Intent(
                InstrumentationRegistry.getTargetContext(),
                PillDetailActivity::class.java))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))

        onView(withText(R.string.action_add_pill))
                .check(matches(isDisplayed()))

        onView(withId(R.id.pillName))
                .check(matches(withInputType(InputType.TYPE_CLASS_TEXT)))
                .check(matches(withHint(R.string.hint_medication_name)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.pillDosage))
                .check(matches(withHint(R.string.hint_medication_dosage)))
                .check(matches(withInputType(InputType.TYPE_CLASS_NUMBER)))
                .check(matches(isDisplayed()))

        onView(withId(R.id.pillDescription))
                .check(matches(withHint(R.string.hint_medication_description)))
                .check(matches(withInputType(InputType.TYPE_CLASS_TEXT)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldDisplayMedicationNameAndDosage() {
        PillDetailActivity.mPillsRepository = mRepository
        val intent = PillDetailActivity.newIntent(InstrumentationRegistry.getTargetContext(), TestData.getFirstPill().id)
        mActivityRule.launchActivity(intent)

        mPillSubject.onSuccess(TestData.getFirstPill())

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))

        onView(withText(R.string.action_edit_pill))
                .check(matches(isDisplayed()))

        onView(withId(R.id.pillName))
                .check(matches(withInputType(InputType.TYPE_CLASS_TEXT)))
                .check(matches(withText(TestData.getFirstPill().name)))

        onView(withId(R.id.pillDosage))
                .check(matches(withText(TestData.getFirstPill().dosage)))
                .check(matches(withInputType(InputType.TYPE_CLASS_NUMBER)))

        onView(withId(R.id.pillDescription))
                .check(matches(withText(TestData.getFirstPill().description)))
                .check(matches(withInputType(InputType.TYPE_CLASS_TEXT)))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldSavePillAfterClickToNavigateUp() {
        PillDetailActivity.mPillsRepository = mRepository
        val pill = TestData.getFirstPill().apply { id = 0 }

        mActivityRule.launchActivity(Intent(
                InstrumentationRegistry.getTargetContext(),
                PillDetailActivity::class.java))

        onView(withId(R.id.pillName))
                .perform(replaceText(pill.name))
                .check(matches(withText(pill.name)))

        onView(withId(R.id.pillDosage))
                .perform(replaceText(pill.dosage))
                .check(matches(withText(pill.dosage)))

        onView(withId(R.id.pillDescription))
                .perform(replaceText(pill.description))
                .check(matches(withText(pill.description)))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .perform(click())

        verify(mRepository).savePill(pill)
    }

    @Test
    fun shouldNotSaveEmptyPillAfterClickToNavigationUp() {
        PillDetailActivity.mPillsRepository = mRepository

        mActivityRule.launchActivity(Intent(
                InstrumentationRegistry.getTargetContext(),
                PillDetailActivity::class.java))

        onView(withId(R.id.pillName))
                .check(matches(withText(EMPTY_TEXT)))

        onView(withId(R.id.pillDosage))
                .check(matches(withText(EMPTY_TEXT)))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .perform(click())

        verify(mRepository, never()).savePill(any())
    }

    @Test
    fun shouldUpdateExistingPillAfterClickToNavigationUp() {
        PillDetailActivity.mPillsRepository = mRepository
        val updatedPill = TestData.getFirstUpdatedPill()

        val intent = PillDetailActivity.newIntent(
                InstrumentationRegistry.getTargetContext(),
                TestData.getFirstPill().id)
        mActivityRule.launchActivity(intent)

        onView(withId(R.id.pillName))
                .perform(replaceText(updatedPill.name))
                .check(matches(withText(updatedPill.name)))

        onView(withId(R.id.pillDosage))
                .perform(replaceText(updatedPill.dosage))
                .check(matches(withText(updatedPill.dosage)))

        onView(withId(R.id.pillDescription))
                .perform(replaceText(updatedPill.description))
                .check(matches(withText(updatedPill.description)))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .perform(click())

        verify(mRepository).updatePill(updatedPill)
    }

    @Test
    fun shouldNotUpdateExistingPillAfterRemoveNameAndDosageAndClickToNavigateUp() {
        PillDetailActivity.mPillsRepository = mRepository
        val updatedPill = TestData.getFirstUpdatedPill()

        val intent = PillDetailActivity.newIntent(
                InstrumentationRegistry.getTargetContext(),
                TestData.getFirstPill().id)
        mActivityRule.launchActivity(intent)

        onView(withId(R.id.pillName))
                .perform(replaceText(EMPTY_TEXT))
                .check(matches(withText(EMPTY_TEXT)))

        onView(withId(R.id.pillDosage))
                .perform(replaceText(EMPTY_TEXT))
                .check(matches(withText(EMPTY_TEXT)))

        onView(withId(R.id.pillDescription))
                .perform(replaceText(updatedPill.description))
                .check(matches(withText(updatedPill.description)))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .perform(click())

        verify(mRepository, never()).updatePill(updatedPill)
    }
}
