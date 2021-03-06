package com.alexzh.medicationreminder

import com.alexzh.medicationreminder.data.model.Pill
import com.alexzh.medicationreminder.data.model.Reminder
import java.util.Date

class TestData {
    companion object {
        val INVALID_ID = -1L

        val FIRST_PILL_ID = 100L
        private val FIRST_PILL_NAME = "pill"
        private val FIRST_PILL_DESCRIPTION = "pill description"
        private val FIRST_PILL_DOSAGE = "100 mg"
        private val FIRST_PILL_UPDATED_NAME = "updated pill"

        val FIRST_REMINDER_ID = 10L
        private val FIRST_START_DATE_REMINDER = Date()
        private val FIRST_END_DATE_REMINDER = Date()
        private val FIRST_REMINDER_TIME = Date()
        private val FIRST_REMINDER_UPDATED_TIME = Date()

        val SECOND_PILL_ID = 200L
        private val SECOND_PILL_NAME = "pill 2"
        private val SECOND_PILL_DESCRIPTION = "pill description 2"
        private val SECOND_PILL_DOSAGE = "200 mg"
        private val SECOND_PILL_UPDATED_NAME = "updated pill 2"

        val SECOND_REMINDER_ID = 20L
        private val SECOND_START_DATE_REMINDER = Date()
        private val SECOND_END_DATE_REMINDER = Date()
        private val SECOND_REMINDER_TIME = Date()

        val EMPTY_LIST_OF_PILLS = listOf<Pill>()
        val EMPTY_LIST_OF_REMINDERS = listOf<Reminder>()
        val EMPTY_LIST_OF_IDS = listOf<Long>()

        fun getFirstPill() : Pill = Pill(
                FIRST_PILL_NAME,
                FIRST_PILL_DESCRIPTION,
                FIRST_PILL_DOSAGE).apply { id = FIRST_PILL_ID }

        fun getFirstUpdatedPill() : Pill = Pill(
                FIRST_PILL_UPDATED_NAME,
                FIRST_PILL_DESCRIPTION,
                FIRST_PILL_DOSAGE).apply { id = FIRST_PILL_ID }

        fun getSecondPill() : Pill = Pill(
                SECOND_PILL_NAME,
                SECOND_PILL_DESCRIPTION,
                SECOND_PILL_DOSAGE).apply { id =  SECOND_PILL_ID}

        fun getSecondUpdatedPill() : Pill = Pill(
                SECOND_PILL_UPDATED_NAME,
                SECOND_PILL_DESCRIPTION,
                SECOND_PILL_DOSAGE).apply { id =  SECOND_PILL_ID}

        fun getFirstReminder() : Reminder = Reminder(
                FIRST_PILL_ID,
                FIRST_START_DATE_REMINDER,
                FIRST_END_DATE_REMINDER,
                FIRST_REMINDER_TIME).apply { reminderId = FIRST_REMINDER_ID }

        fun getFirstUpdatedReminder() : Reminder = Reminder(
                FIRST_PILL_ID,
                FIRST_START_DATE_REMINDER,
                FIRST_END_DATE_REMINDER,
                FIRST_REMINDER_UPDATED_TIME).apply { reminderId = FIRST_REMINDER_ID }

        fun getSecondReminder() : Reminder = Reminder(
                SECOND_PILL_ID,
                SECOND_START_DATE_REMINDER,
                SECOND_END_DATE_REMINDER,
                SECOND_REMINDER_TIME).apply { reminderId = SECOND_REMINDER_ID }

        fun getReminders(): List<Reminder> = listOf(getFirstReminder(), getSecondReminder())

        fun getPills() : List<Pill> = listOf(getFirstPill(), getSecondPill())
    }
}