package com.alexzh.medicationreminder.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.PillDao
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PillDaoTest {

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
    fun should_getEmptyListOfPills_fromEmptyTable() {
        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_getPillById_afterInserting() {
        mPillDao.insertPill(TestData.getFirstPill())

        mPillDao.getPillById(TestData.getFirstPill().id)
                .test()
                .assertValue(TestData.getFirstPill())
    }

    @Test
    fun should_getError_afterLoadingPillByIdFromNonExistingPill() {
        mPillDao.getPillById(TestData.getFirstPill().id)
                .test()
                .assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun should_insert_newPill() {
        mPillDao.insertPill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_notInsert_theSamePillTwice() {
        mPillDao.insertPill(TestData.getFirstPill())
        mPillDao.insertPill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_notInsert_emptyListOfPills() {
        mPillDao.insertPills(TestData.EMPTY_LIST_OF_PILLS)

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_insert_listOfPills() {
        mPillDao.insertPills(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_notInsert_listOfPillsTwice() {
        mPillDao.insertPills(TestData.getPills())
        mPillDao.insertPills(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_update_existingPill() {
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
    fun should_notUpdate_nonExistingPill() {
        mPillDao.updatePill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_existingPill() {
        mPillDao.insertPill(TestData.getFirstPill())
        mPillDao.insertPill(TestData.getSecondPill())

        mPillDao.deletePill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getSecondPill()))
    }

    @Test
    fun should_notDelete_NonExistingPill () {
        mPillDao.deletePill(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_allPills() {
        mPillDao.insertPills(TestData.getPills())

        mPillDao.deleteAllPills()

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_notDelete_allPillsFromEmptyTable() {
        mPillDao.deleteAllPills()

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }
}