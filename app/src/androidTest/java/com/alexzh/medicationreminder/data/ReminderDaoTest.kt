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
    fun shouldLoadRemindersFromEmptyTable() {
        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun shouldInsertReminder() {
        mReminderDao.insert(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun shouldInsertTheSameReminderTwice() {
        mReminderDao.insert(TestData.getFirstReminder())
        mReminderDao.insert(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun shouldInsertListOfReminders() {
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun shouldInsertListOfRemindersTwice() {
        mReminderDao.insert(TestData.getReminders())
        mReminderDao.insert(TestData.getReminders())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun shouldUpdateNonExistingReminder() {
        mReminderDao.update(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun shouldUpdateExistingReminder() {
        mReminderDao.insert(TestData.getFirstReminder())

        val reminder = TestData.getFirstReminder().copy(startDate = Date())
        mReminderDao.update(reminder)

        mReminderDao.getReminders()
                .test()
                .assertValue(listOf(reminder))
    }

    @Test
    fun shouldNotDeleteReminderFromEmptyTable() {
        mReminderDao.delete(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun shouldDeleteExistingReminder() {
        mReminderDao.insert(TestData.getFirstReminder())

        mReminderDao.delete(TestData.getFirstReminder())

        mReminderDao.getReminders()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }
}