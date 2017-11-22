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
        mPillsRepository.insertPills(TestData.getPills(), TestData.getReminders())

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_getPillById_afterInsertingPill() {
        mPillsRepository.insertPill(TestData.getFirstPill(), TestData.getFirstReminder())

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
        mPillsRepository.insertPill(TestData.getFirstPill(), TestData.getFirstReminder())

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf(TestData.getFirstPill()))
    }

    @Test
    fun should_insert_nonExistingListOfPills() {
        mPillsRepository.insertPills(TestData.getPills(), TestData.getReminders())

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.getPills())
    }

    @Test
    fun should_update_existingPill() {
        mPillsRepository.insertPill(TestData.getFirstPill(), TestData.getFirstReminder())

        val updatedPill = TestData.getFirstPill().copy(name = "updated pill name")
        mPillsRepository.updatePill(updatedPill)

        mPillsRepository.getPills()
                .test()
                .assertValue(listOf(updatedPill))
    }

    @Test
    fun should_delete_existingPill() {
        mPillsRepository.insertPill(TestData.getFirstPill(), TestData.getFirstReminder())

        mPillsRepository.deletePill(TestData.getFirstPill())

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }

    @Test
    fun should_delete_allPills() {
        mPillsRepository.insertPills(TestData.getPills(), TestData.getReminders())

        mPillsRepository.deleteAllPills()

        mPillsRepository.getPills()
                .test()
                .assertValue(TestData.EMPTY_LIST_OF_PILLS)
    }
}