package com.alexzh.medicationreminder.app

import android.support.test.espresso.Espresso.onView
import android.support.test.espresso.Espresso.openContextualActionModeOverflowMenu
import android.support.test.espresso.action.ViewActions.click
import android.support.test.espresso.assertion.ViewAssertions.matches
import android.support.test.espresso.contrib.RecyclerViewActions.actionOnItemAtPosition
import android.support.test.espresso.intent.Intents
import android.support.test.espresso.intent.Intents.intended
import android.support.test.espresso.intent.matcher.IntentMatchers.hasComponent
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.espresso.matcher.ViewMatchers.isDisplayed
import android.support.test.espresso.matcher.ViewMatchers.withId
import android.support.test.espresso.matcher.ViewMatchers.withText
import android.support.test.rule.ActivityTestRule
import android.support.test.runner.AndroidJUnit4
import android.support.v7.widget.RecyclerView
import android.view.WindowManager
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.home.HomeActivity
import com.alexzh.medicationreminder.pilldetail.PillDetailActivity
import com.alexzh.medicationreminder.settings.SettingsActivity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Completable
import io.reactivex.subjects.SingleSubject
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
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

            whenever(mRepository.getPills()).thenReturn(mPillsSubject)
            whenever(mRepository.getPillById(any())).thenReturn(mPillSubject)
            whenever(mRepository.savePill(any())).thenReturn(Completable.complete())
        }
    }

    @Before
    fun setUp() {
        val activity = mActivityRule.activity
        val wakeUpDevice = Runnable {
            activity.window.addFlags(
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                            WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                            WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        }
        activity.runOnUiThread(wakeUpDevice)
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

    @Test
    fun shouldCheckSettingIntent() {
        Intents.init()

        openContextualActionModeOverflowMenu()

        onView(withText(R.string.action_settings))
                .check(matches(isDisplayed()))
                .perform(click())

        intended(hasComponent(SettingsActivity::class.java.name))

        Intents.release()
    }
}