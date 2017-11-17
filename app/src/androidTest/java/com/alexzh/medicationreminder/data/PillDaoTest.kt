package com.alexzh.medicationreminder.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.model.Pill
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PillDaoTest {

    companion object {
        val EMPTY_PILL_LIST = listOf<Pill>()
    }

    private lateinit var mDatabase: MedicationReminderDatabase

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        mDatabase = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
    }

    @Test
    fun shouldInsertPill() {
        mDatabase.pillDao().insertPill(TestData.getFirstPill())

        mDatabase.pillDao().getPills()
                           .test()
                           .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun shouldUpdateExistingPill() {
        mDatabase.pillDao().insertPill(TestData.getSecondPill())

        val pill = TestData.getSecondPill().copy(
                name = "updated second pill name",
                description = "updated second pill description")

        mDatabase.pillDao().updatePill(pill)

        mDatabase.pillDao().getPills()
                .test()
                .assertValue(listOf(pill))
    }

    @Test
    fun shouldUpdateNotInsertNewPill() {
        mDatabase.pillDao().updatePill(TestData.getFirstPill())

        mDatabase.pillDao().getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }
}