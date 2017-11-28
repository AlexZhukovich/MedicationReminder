package com.alexzh.medicationreminder.data

import android.arch.core.executor.testing.InstantTaskExecutorRule
import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.PillDao
import com.alexzh.medicationreminder.data.local.ReminderDao
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PillDaoTest {

    private lateinit var mPillDao: PillDao
    private lateinit var mReminderDao: ReminderDao

    @Rule @JvmField
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mReminderDao = database.reminderDao()
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
        mReminderDao.insert(TestData.getFirstReminder())
        val pillId = mPillDao.insert(TestData.getFirstPill())

        assertEquals(TestData.FIRST_PILL_ID, pillId)
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
        mReminderDao.insert(TestData.getFirstReminder())
        val pillId = mPillDao.insert(TestData.getFirstPill())

        assertEquals(TestData.FIRST_PILL_ID, pillId)
        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_notInsert_theSamePillTwice() {
        mReminderDao.insert(TestData.getFirstReminder())

        mPillDao.insert(TestData.getFirstPill())
        mPillDao.insert(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_notInsert_emptyListOfPills() {
        mPillDao.insert(TestData.EMPTY_LIST_OF_PILLS)

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_insert_listOfPills() {
        mReminderDao.insert(TestData.getReminders())
        mPillDao.insert(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_notInsert_listOfPillsTwice() {
        mReminderDao.insert(TestData.getReminders())
        mPillDao.insert(TestData.getPills())
        mPillDao.insert(TestData.getPills())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_update_existingPill() {
        mReminderDao.insert(TestData.getSecondReminder())
        mPillDao.insert(TestData.getSecondPill())

        mPillDao.update(TestData.getSecondUpdatedPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getSecondUpdatedPill()))
    }

    @Test
    fun should_notUpdate_nonExistingPill() {
        mPillDao.update(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_existingPill() {
        mReminderDao.insert(TestData.getFirstReminder())
        mReminderDao.insert(TestData.getSecondReminder())

        mPillDao.insert(TestData.getFirstPill())
        mPillDao.insert(TestData.getSecondPill())

        mPillDao.delete(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(listOf(TestData.getSecondPill()))
    }

    @Test
    fun should_notDelete_NonExistingPill () {
        mPillDao.delete(TestData.getFirstPill())

        mPillDao.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_allPills() {
        mReminderDao.insert(TestData.getReminders())
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