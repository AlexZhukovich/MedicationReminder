@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class PillDetailPresenterTest {

    companion object {
        val PILL_ID = 1L
    }

    private val repository = mock<PillsRepository>()
    private val presenter = PillDetailPresenter(repository)

    @Test
    fun `Call repository during loading data`() {
        presenter.loadPillInfo(PILL_ID)

        verify(repository).getPillById(any())
    }

    @Test
    fun `Call repository during loading data with pill ID`() {
        presenter.loadPillInfo(PILL_ID)

        verify(repository).getPillById(PILL_ID)
    }
}