@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.data.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DateConverterTest {

    companion object {
        val DATE_IN_LONG = 1511049600000L
    }

    private val converter = DateConverter()

    @Test
    fun `Verify converting Long to Date`() {
        assertEquals(Date(DATE_IN_LONG), converter.fromTimeStamp(DATE_IN_LONG))
    }

    @Test
    fun `Verify converting Date to Long`() {
        val date = Date()
        date.time = DATE_IN_LONG

        assertEquals(DATE_IN_LONG, converter.fromDate(date))
    }
}