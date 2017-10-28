package com.alexzh.medicationreminder

import android.support.test.espresso.NoMatchingViewException
import android.support.test.espresso.ViewAssertion
import android.support.v7.widget.RecyclerView
import android.view.View
import org.junit.Assert

class RecyclerViewItemCountAssertion(expectedCount: Int) : ViewAssertion {
    private val mExpectedCount = expectedCount

    override fun check(view: View, noViewFoundException: NoMatchingViewException?) {
        if (noViewFoundException != null) {
            throw noViewFoundException
        }

        val recyclerView = view as RecyclerView
        val adapter = recyclerView.adapter
        Assert.assertEquals(mExpectedCount, adapter.itemCount)
    }
}