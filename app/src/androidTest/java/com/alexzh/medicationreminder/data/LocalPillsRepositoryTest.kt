package com.alexzh.medicationreminder.data

import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.LocalPillsRepository
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.PillDao
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalPillsRepositoryTest {

    private lateinit var mPillsRepository: PillsRepository
    private lateinit var mPillsDao: PillDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mPillsDao = database.pillDao()
        mPillsRepository = LocalPillsRepository(mPillsDao)
    }

    @Test
    fun shouldGetAllPills() {
        mPillsDao.insertPills(TestData.getPills())

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun shouldGetExistingPillById() {
        mPillsDao.insertPill(TestData.getFirstPill())

        mPillsRepository.getPillById(TestData.getFirstPill().id)
                .test()
                .assertValue(TestData.getFirstPill())
    }

    @Test
    fun shouldReturnErrorForNonExistingPillById() {
        mPillsRepository.getPillById(TestData.getFirstPill().id)
                .test()
                .assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun shouldInsertNewPill() {
        mPillsRepository.insertPill(TestData.getFirstPill())

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun shouldInsertListOfPills() {
        mPillsRepository.insertPills(TestData.getPills())

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun shouldUpdateExistingPill() {
        mPillsRepository.insertPill(TestData.getFirstPill())

        val updatedPill = TestData.getFirstPill().copy(name = "updated pill name")
        mPillsRepository.updatePill(updatedPill)

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf(updatedPill))
    }

    @Test
    fun shouldDeleteExistingPill() {
        mPillsRepository.insertPill(TestData.getFirstPill())

        mPillsRepository.deletePill(TestData.getFirstPill())

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf())
    }
}