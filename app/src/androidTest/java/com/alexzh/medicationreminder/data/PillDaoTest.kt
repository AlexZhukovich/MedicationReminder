package com.alexzh.medicationreminder.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PillDaoTest {
    private lateinit var mDatabase: MedicationReminderDatabase

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Test
    fun shouldNoInsertedPills() {
        val context = InstrumentationRegistry.getContext()
        mDatabase = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                        .allowMainThreadQueries()
                        .build()

        mDatabase.pillDao().getPills()
                           .test()
                           .assertValue(listOf())
    }

    @Test
    fun shouldInsertPill() {
        val context = InstrumentationRegistry.getContext()
        mDatabase = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()

        mDatabase.pillDao().insertPill(TestData.getFirstPill())

        mDatabase.pillDao().getPills()
                           .test()
                           .assertValue(listOf(TestData.getFirstPill()))
    }
}