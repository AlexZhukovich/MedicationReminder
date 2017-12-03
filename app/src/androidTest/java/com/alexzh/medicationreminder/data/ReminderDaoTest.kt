package com.alexzh.medicationreminder.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.PillDao
import com.alexzh.medicationreminder.data.local.ReminderDao
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ReminderDaoTest {

    private lateinit var mReminderDao : ReminderDao
    private lateinit var mPillDao : PillDao

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
    fun should_getEmpyListOfReminders_fromEmptyTable() {
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_getEmptyListOfReminder_forNonExistingPill() {
        val reminders = mReminderDao.getRemindersByPillId(TestData.INVALID_ID)

        assertEquals(TestData.EMPTY_LIST_OF_REMINDERS, reminders)
    }

    @Test
    fun should_getListOfReminders_forExistingPill() {
        val pillId = mPillDao.insert(TestData.getFirstPill())
        mReminderDao.insert(TestData.getFirstReminder())

        val reminders = mReminderDao.getRemindersByPillId(pillId)
        assertEquals(listOf(TestData.getFirstReminder()), reminders)
    }

    @Test
    fun should_getEmptyListOfReminders_forExistingPillWithoutReminders() {
        val pillId = mPillDao.insert(TestData.getFirstPill())

        val reminders = mReminderDao.getRemindersByPillId(pillId)
        assertEquals(TestData.EMPTY_LIST_OF_REMINDERS, reminders)
    }

    @Test
    fun should_insert_newReminder() {
        mPillDao.insert(TestData.getFirstPill())
        val reminderId = mReminderDao.insert(TestData.getFirstReminder())

        assertEquals(TestData.FIRST_REMINDER_ID, reminderId)
        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun should_notInsert_theSameReminderTwice() {
        mPillDao.insert(TestData.getFirstPill())

        mReminderDao.insert(TestData.getFirstReminder())
        mReminderDao.insert(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun should_insert_listOfReminders() {
        mPillDao.insert(TestData.getPills())

        val expectedIds = listOf(TestData.FIRST_REMINDER_ID, TestData.SECOND_REMINDER_ID)
        val ids = mReminderDao.insert(TestData.getReminders())

        assertEquals(expectedIds, ids)
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun should_notInsert_listOfRemindersTwice() {
        mPillDao.insert(TestData.getPills())
        mReminderDao.insert(TestData.getReminders())
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun should_notUpdate_nonExistingReminder() {
        val count = mReminderDao.update(TestData.getFirstReminder())

        assertEquals(0, count)
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_update_existingReminder() {
        mPillDao.insert(TestData.getFirstPill())
        mReminderDao.insert(TestData.getFirstReminder())

        val count = mReminderDao.update(TestData.getFirstUpdatedReminder())

        assertEquals(1, count)
        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstUpdatedReminder()))
    }

    @Test
    fun should_notDelete_reminderFromEmptyTable() {
        val count = mReminderDao.delete(TestData.getFirstReminder())

        assertEquals(0, count)
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_delete_existingReminder() {
        mPillDao.insert(TestData.getPills())
        mReminderDao.insert(TestData.getReminders())
        val count = mReminderDao.delete(TestData.getFirstReminder())

        assertEquals(1, count)
        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getSecondReminder()))
    }

    @Test
    fun should_notDelete_ListOfRemindersFromEmptyTable() {
        val count = mReminderDao.delete(TestData.getReminders())

        assertEquals(0, count)
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_delete_existingReminders() {
        mPillDao.insert(TestData.getPills())
        mReminderDao.insert(TestData.getReminders())
        val count = mReminderDao.delete(listOf(TestData.getFirstReminder()))

        assertEquals(1, count)
        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getSecondReminder()))
    }

    @Test
    fun should_notDelete_allRemindersFromEmptyTable() {
        mReminderDao.deleteAll()

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_delete_allRemindersFromFilledTable() {
        mPillDao.insert(TestData.getPills())
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.deleteAll()

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }
}