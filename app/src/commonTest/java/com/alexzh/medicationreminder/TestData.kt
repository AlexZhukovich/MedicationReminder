package com.alexzh.medicationreminder

import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.data.model.Reminder
import java.util.Date

class TestData {
    companion object {
        private val FIRST_PILL_ID = 1L
        private val FIRST_PILL_NAME = "pill"
        private val FIRST_PILL_DESCRIPTION = "pill description"
        private val FIRST_PILL_DOSAGE = "100 mg"

        private val FIRST_REMINDER_ID = 10L
        private val FIRST_START_DATE_REMINDER = Date()
        private val FIRST_END_DATE_REMINDER = Date()
        private val FIRST_REMINDER_TIME = Date()

        private val SECOND_PILL_ID = 2L
        private val SECOND_PILL_NAME = "pill 2"
        private val SECOND_PILL_DESCRIPTION = "pill description 2"
        private val SECOND_PILL_DOSAGE = "200 mg"

        private val SECOND_REMINDER_ID = 20L
        private val SECOND_START_DATE_REMINDER = Date()
        private val SECOND_END_DATE_REMINDER = Date()
        private val SECOND_REMINDER_TIME = Date()

        val EMPTY_LIST_OF_PILLS = listOf<Pill>()
        val EMPTY_LIST_OF_REMINDERS = listOf<Reminder>()

        fun getFirstPill() : Pill = Pill(
                FIRST_PILL_ID,
                FIRST_PILL_NAME,
                FIRST_PILL_DESCRIPTION,
                FIRST_PILL_DOSAGE,
                FIRST_REMINDER_ID)

        fun getSecondPill() : Pill = Pill(
                SECOND_PILL_ID,
                SECOND_PILL_NAME,
                SECOND_PILL_DESCRIPTION,
                SECOND_PILL_DOSAGE,
                SECOND_REMINDER_ID)

        fun getFirstReminder() : Reminder = Reminder(
                FIRST_REMINDER_ID,
                FIRST_START_DATE_REMINDER,
                FIRST_END_DATE_REMINDER,
                FIRST_REMINDER_TIME)

        fun getSecondReminder() : Reminder = Reminder(
                SECOND_REMINDER_ID,
                SECOND_START_DATE_REMINDER,
                SECOND_END_DATE_REMINDER,
                SECOND_REMINDER_TIME)

        fun getReminders(): List<Reminder> = listOf(getFirstReminder(), getSecondReminder())

        fun getPills() : List<Pill> = listOf(getFirstPill(), getSecondPill())
    }
}