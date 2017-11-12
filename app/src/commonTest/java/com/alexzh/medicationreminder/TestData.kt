package com.alexzh.medicationreminder

import com.alexzh.medicationreminder.data.model.Pill

class TestData {
    companion object {
        private val FIRST_PILL_ID = 1L
        private val FIRST_PILL_NAME = "pill"
        private val FIRST_PILL_DESCRIPTION = "pill description"
        private val FIRST_PILL_DOSAGE = "100 mg"

        private val SECOND_PILL_ID = 2L
        private val SECOND_PILL_NAME = "pill 2"
        private val SECOND_PILL_DESCRIPTION = "pill description 2"
        private val SECOND_PILL_DOSAGE = "200 mg"

        fun getFirstPill() : Pill = Pill(FIRST_PILL_ID, FIRST_PILL_NAME, FIRST_PILL_DESCRIPTION, FIRST_PILL_DOSAGE)

        fun getSecondPill() : Pill = Pill(SECOND_PILL_ID, SECOND_PILL_NAME, SECOND_PILL_DESCRIPTION, SECOND_PILL_DOSAGE)

        fun getPills() : List<Pill> = listOf(getFirstPill(), getSecondPill())
    }
}