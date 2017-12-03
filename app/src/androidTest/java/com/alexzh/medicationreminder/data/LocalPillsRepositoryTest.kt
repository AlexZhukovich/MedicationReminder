package com.alexzh.medicationreminder.data

import android.arch.persistence.room.EmptyResultSetException
import android.arch.persistence.room.Room
import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.local.LocalPillsRepository
import com.alexzh.medicationreminder.data.local.MedicationReminderDatabase
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LocalPillsRepositoryTest {

    private lateinit var mPillsRepository: PillsRepository

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getContext()
        val database = Room.inMemoryDatabaseBuilder(context, MedicationReminderDatabase::class.java)
                .allowMainThreadQueries()
                .build()
        mPillsRepository = LocalPillsRepository(database.pillDao(), database.reminderDao())
    }

    @Test
    fun should_getPills_afterInserting() {
        mPillsRepository.savePills(TestData.getPills())
                .test()

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_getReminders_fromEmptyTable() {
        mPillsRepository.getReminder()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_REMINDERS)
    }

    @Test
    fun should_getReminder_afterInserting() {
        mPillsRepository.savePill(TestData.getFirstPill())
                .test()

        mPillsRepository.saveReminder(TestData.getFirstReminder())
                .test()

        mPillsRepository.getReminder()
                .test()
                .assertValue(listOf(TestData.getFirstReminder()))
    }

    @Test
    fun should_getPillById_afterInsertingPill() {
        mPillsRepository.savePill(TestData.getFirstPill())
                .test()

        mPillsRepository.getPillById(TestData.getFirstPill().id)
                .test()
                .assertValue(TestData.getFirstPill())
    }

    @Test
    fun should_returnError_afterLoadingPillByIdForNonExistingPill() {
        mPillsRepository.getPillById(TestData.getFirstPill().id)
                .test()
                .assertError(EmptyResultSetException::class.java)
    }

    @Test
    fun should_insert_nonExistingPill() {
        mPillsRepository.savePill(TestData.getFirstPill())
                .test()

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_insert_nonExistingListOfPills() {
        mPillsRepository.savePills(TestData.getPills())
                .test()

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_insert_nonExistingListOfReminders() {
        mPillsRepository.savePills(TestData.getPills())
                .test()

        mPillsRepository.saveReminders(TestData.getReminders())
                .test()

        mPillsRepository.getReminder()
                .test()
                .assertValue(TestData.getReminders())
    }

    @Test
    fun should_update_existingPill() {
        mPillsRepository.savePill(TestData.getFirstPill())
                .test()

        mPillsRepository.updatePill(TestData.getFirstUpdatedPill())
                .test()

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstUpdatedPill()))
    }

    @Test
    fun should_delete_existingPill() {
        mPillsRepository.savePill(TestData.getFirstPill())

        mPillsRepository.removePill(TestData.getFirstPill())

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }
}