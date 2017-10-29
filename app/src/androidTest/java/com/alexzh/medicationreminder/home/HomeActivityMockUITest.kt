package com.alexzh.medicationreminder.home

import android.support.test.espresso.Espresso
import android.support.test.espresso.matcher.ViewMatchers
import android.support.test.rule.ActivityTestRule
import com.alexzh.medicationreminder.R
import com.alexzh.medicationreminder.RecyclerViewItemCountAssertion
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Rule
import org.junit.Test

class HomeActivityMockUITest {

    private val mRepository = mock<PillsRepository>()

    @Rule @JvmField
    val mActivityRule = object: ActivityTestRule<HomeActivity>(HomeActivity::class.java) {
        override fun beforeActivityLaunched() {
            HomeActivity.mPillsRepository = mRepository

            whenever(mRepository.getMorePills()).thenReturn(Single.just(listOf(Pill("title", "description"))))
        }
    }

    @Test
    fun shouldDisplayTestPill() {
        Espresso.onView(ViewMatchers.withId(R.id.recyclerView))
                .check(RecyclerViewItemCountAssertion(1))
    }
}