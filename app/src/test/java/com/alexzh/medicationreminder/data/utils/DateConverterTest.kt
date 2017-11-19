@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.data.utils

import org.junit.Assert.assertEquals
import org.junit.Test
import java.util.Date

class DateConverterTest {

    @Test
    fun `Verify converting Long to Date`() {
        val converter = DateConverter()
        val dateLong = 1511049600000L

        assertEquals(Date(dateLong), converter.fromTimeStamp(dateLong))
    }

    @Test
    fun `Verify converting Date to Long`() {
        val converter = DateConverter()
        val dateLong = 1511049600000L

        val date = Date()
        date.time = dateLong

        assertEquals(dateLong, converter.fromDate(date))
    }
}