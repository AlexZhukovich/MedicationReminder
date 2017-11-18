package com.alexzh.medicationreminder.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.LocalPillsRepository
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalPillsRepositoryTest {

    @Test
    fun shouldGetAllPills() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        val pillsDao = database.pillDao()
        val pillsRepository = LocalPillsRepository(pillsDao)

        pillsDao.insertPills(TestData.getPills())

        pillsRepository.getMorePills()
                .test()
                .assertValue(TestData.getPills())
    }
}