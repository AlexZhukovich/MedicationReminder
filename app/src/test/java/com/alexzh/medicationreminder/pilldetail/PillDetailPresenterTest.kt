@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.subjects.SingleSubject
import org.junit.Test

class PillDetailPresenterTest {

    companion object {
        val PILL_ID = 1L
        val PILL_NAME = "pill"
        val PILL_DESCRIPTION = "pill description"
    }

    private val pillSubject = SingleSubject.create<Pill>()

    private val view = mock<PillDetail.View>()
    private val repository = mock<PillsRepository>().apply {
        whenever(this.getPillById(any())).thenReturn(pillSubject)
    }

    private val presenter = PillDetailPresenter(repository, view)

    @Test
    fun `Call repository during loading data with pill ID`() {
        presenter.loadPillInfo(PILL_ID)

        verify(repository).getPillById(PILL_ID)
    }

    @Test
    fun `Display pill info after loading`() {
        presenter.loadPillInfo(PILL_ID)

        pillSubject.onSuccess(Pill(PILL_NAME, PILL_DESCRIPTION))

        verify(view).showPillInfo(any())
    }
}