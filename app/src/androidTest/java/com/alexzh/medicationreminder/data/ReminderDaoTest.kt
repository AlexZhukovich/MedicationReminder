package com.alexzh.medicationreminder.data

import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import com.alexzh.medicationreminder.data.local.ReminderDao
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.util.Date

@RunWith(AndroidJUnit4::class)
class ReminderDaoTest {

    private lateinit var mReminderDao : ReminderDao

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mReminderDao = database.reminderDao()
    }

    @Test
    fun should_getEmpyListOfReminders_fromEmptyTable() {
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_insert_newReminder() {
        mReminderDao.insert(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun should_notInsert_theSameReminderTwice() {
        mReminderDao.insert(TestData.getFirstReminder())
        mReminderDao.insert(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun should_insert_listOfReminders() {
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun should_notInsert_listOfRemindersTwice() {
        mReminderDao.insert(TestData.getReminders())
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun should_notUpdate_nonExistingReminder() {
        mReminderDao.update(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_update_existingReminder() {
        mReminderDao.insert(TestData.getFirstReminder())

        val reminder = TestData.getFirstReminder().copy(startDate = Date())
        mReminderDao.update(reminder)

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(reminder))
    }

    @Test
    fun should_notDelete_reminderFromEmptyTable() {
        mReminderDao.delete(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_delete_existingReminder() {
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.delete(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getSecondReminder()))
    }

    @Test
    fun should_notDelete_ListOfRemindersFromEmptyTable() {
        mReminderDao.delete(TestData.getReminders())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_delete_existingReminders() {
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.delete(listOf(TestData.getFirstReminder()))

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
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.deleteAll()

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }
}