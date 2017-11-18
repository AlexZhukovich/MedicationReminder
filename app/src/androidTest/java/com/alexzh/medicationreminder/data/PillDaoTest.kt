package com.alexzh.medicationreminder.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.PillDao
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

    private lateinit var mPillDao: PillDao

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mPillDao = database.pillDao()
    }

    @Test
    fun shouldLoadPillsFromEmptyTable() {
        mPillDao.getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }

    @Test
    fun shouldLoadExistingPillById() {
        mPillDao.insertPill(TestData.getFirstPill())

        mPillDao.getPillById(TestData.getFirstPill().id)
                .test()
                .assertValue(TestData.getFirstPill())
    }

    @Test
    fun shouldLoadNonExistingPillById() {
        mPillDao.getPillById(TestData.getFirstPill().id)
                .test()
                .assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun shouldInsertPill() {
        mPillDao.insertPill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun shouldInsertTheSamePillTwice() {
        mPillDao.insertPill(TestData.getFirstPill())
        mPillDao.insertPill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun shouldInsertEmptyListOfPills() {
        mPillDao.insertPills(EMPTY_PILL_LIST)

        mPillDao.getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }

    @Test
    fun shouldInsertListOfPills() {
        mPillDao.insertPills(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun shouldInsertListOfPillsTwice() {
        mPillDao.insertPills(TestData.getPills())
        mPillDao.insertPills(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun shouldUpdateExistingPill() {
        mPillDao.insertPill(TestData.getSecondPill())

        val pill = TestData.getSecondPill().copy(
                name = "updated second pill name",
                description = "updated second pill description")

        mPillDao.updatePill(pill)

        mPillDao.getPills()
                .test()
                .assertValue(listOf(pill))
    }

    @Test
    fun shouldUpdateNotInsertNewPill() {
        mPillDao.updatePill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }

    @Test
    fun shouldRemovePill() {
        mPillDao.insertPill(TestData.getFirstPill())
        mPillDao.insertPill(TestData.getSecondPill())

        mPillDao.deletePill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getSecondPill()))

    }

    @Test
    fun shouldPillsTableStayEmptyAfterRemovingNonExistingPill () {
        mPillDao.deletePill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }

    @Test
    fun shouldRemoveAllPills() {
        mPillDao.insertPills(TestData.getPills())

        mPillDao.deleteAllPills()

        mPillDao.getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }

    @Test
    fun shouldPillsTableStayEmptyAfterRemovingAllPillsFromEmptyTable() {
        mPillDao.deleteAllPills()

        mPillDao.getPills()
                .test()
                .assertValue(EMPTY_PILL_LIST)
    }
}