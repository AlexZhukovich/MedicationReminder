@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.subjects.SingleSubject
import org.junit.Test
import java.lang.RuntimeException

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

    @Test
    fun `Show error message after loading error`() {
        presenter.loadPillInfo(PILL_ID)

        pillSubject.onError(RuntimeException())

        verify(view).showErrorMessage()
    }

    @Test
    fun `Don't show results and error message after loading interruption`() {
        presenter.loadPillInfo(PILL_ID)
        presenter.onDestroy()

        verify(view, never()).showErrorMessage()
        verify(view, never()).showPillInfo(any())
    }
}