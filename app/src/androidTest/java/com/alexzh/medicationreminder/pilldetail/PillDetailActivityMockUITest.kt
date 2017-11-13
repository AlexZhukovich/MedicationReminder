package com.alexzh.medicationreminder.pilldetail

import android.content.Intent
import android.support.test.InstrumentationRegistry
import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withHint
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.espresso.matcher.ViewMatchers.withContentDescription
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Rule
import org.junit.Test

class PillDetailActivityMockUITest {

    companion object {
        val NAVIGATE_UP_DESCRIPTION = "Navigate up"
    }

    private val mPillSubject = SingleSubject.create<Pill>()
    private val mRepository = mock<PillsRepository>().apply {
        whenever(this.getPillById(any())).thenReturn(mPillSubject)
    }

    @Rule @JvmField
    val mActivityRule = ActivityTestRule<PillDetailActivity>(PillDetailActivity::class.java, true, false)

    @Test
    fun shouldCheckToolbarForNewPill() {
        mActivityRule.launchActivity(Intent(
                InstrumentationRegistry.getTargetContext(),
                PillDetailActivity::class.java))

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))

        onView(withText(R.string.action_add_pill))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldCheckMedicationNameHintForNewPill() {
        mActivityRule.launchActivity(Intent(
                InstrumentationRegistry.getTargetContext(),
                PillDetailActivity::class.java))

        onView(withId(R.id.pillName))
                .check(matches(withHint(R.string.hint_medication_name)))
    }

    @Test
    fun shouldCheckMedicationDosageHint() {
        mActivityRule.launchActivity(Intent(
                InstrumentationRegistry.getTargetContext(),
                PillDetailActivity::class.java))

        onView(withId(R.id.pillDosage))
                .check(matches(withHint(R.string.hint_medication_dosage)))
    }

    @Test
    fun shouldCheckToolbarForEditPill() {
        val intent = Intent(InstrumentationRegistry.getTargetContext(), PillDetailActivity::class.java).apply {
            putExtra("pill_id_key", TestData.getFirstPill().id)
        }
        mActivityRule.launchActivity(intent)

        onView(withContentDescription(NAVIGATE_UP_DESCRIPTION))
                .check(matches(isDisplayed()))

        onView(withText(R.string.action_edit_pill))
                .check(matches(isDisplayed()))
    }

    @Test
    fun shouldCheckNameAndDosagePill() {
        PillDetailActivity.mPillsRepository = mRepository
        val intent = Intent(InstrumentationRegistry.getTargetContext(), PillDetailActivity::class.java).apply {
            putExtra("pill_id_key", TestData.getFirstPill().id)
        }
        mActivityRule.launchActivity(intent)

        mPillSubject.onSuccess(TestData.getFirstPill())

        onView(withId(R.id.pillName))
                .check(matches(withText(TestData.getFirstPill().name)))

        onView(withId(R.id.pillDosage))
                .check(matches(withText(TestData.getFirstPill().dosage)))
    }
}
