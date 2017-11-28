package com.alexzh.medicationreminder.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.PillDao
import org.junit.Assert.assertEquals
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
        mPillDao.insert(TestData.getFirstPill())

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
        val pillId = mPillDao.insert(TestData.getFirstPill())

        assertEquals(TestData.FIRST_PILL_ID, pillId)
        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_notInsert_theSamePillTwice() {
        mPillDao.insert(TestData.getFirstPill())
        mPillDao.insert(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_notInsert_emptyListOfPills() {
        val ids = mPillDao.insert(TestData.EMPTY_LIST_OF_PILLS)

        assertEquals(TestData.EMPTY_LIST_OF_IDS, ids)
        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_insert_listOfPills() {
        val expectedIds = listOf(TestData.FIRST_PILL_ID, TestData.SECOND_PILL_ID)
        val ids = mPillDao.insert(TestData.getPills())

        assertEquals(expectedIds, ids)
        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_notInsert_listOfPillsTwice() {
        mPillDao.insert(TestData.getPills())
        mPillDao.insert(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_update_existingPill() {
        mPillDao.insert(TestData.getSecondPill())

        val count = mPillDao.update(TestData.getSecondUpdatedPill())

        assertEquals(1, count)
        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getSecondUpdatedPill()))
    }

    @Test
    fun should_notUpdate_nonExistingPill() {
        val count = mPillDao.update(TestData.getFirstPill())

        assertEquals(0, count)
        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_existingPill() {
        mPillDao.insert(TestData.getFirstPill())
        mPillDao.insert(TestData.getSecondPill())

        val count = mPillDao.delete(TestData.getFirstPill())

        assertEquals(1, count)
        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getSecondPill()))
    }

    @Test
    fun should_notDelete_NonExistingPill () {
        val count = mPillDao.delete(TestData.getFirstPill())

        assertEquals(0, count)
        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_allPills() {
        mPillDao.insert(TestData.getPills())

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