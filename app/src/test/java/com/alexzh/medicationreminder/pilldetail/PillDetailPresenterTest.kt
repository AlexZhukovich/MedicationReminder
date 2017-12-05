@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.pilldetail

import com.alexzh.medicationreminder.TestData
import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import org.junit.Test
import java.lang.RuntimeException

class PillDetailPresenterTest {

    private val pillSubject = SingleSubject.create<Pill>()

    private val view = mock<PillDetail.View>()
    private val repository = mock<PillsRepository>().apply {
        whenever(this.getPillById(any())).thenReturn(pillSubject)
    }

    private val presenter = PillDetailPresenter(view, repository, Schedulers.trampoline(), Schedulers.trampoline())

    @Test
    fun `Call repository during loading data with pill ID`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        verify(repository).getPillById(TestData.getFirstPill().id)
    }

    @Test
    fun `Display pill info after loading`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        pillSubject.onSuccess(TestData.getFirstPill())

        verify(view).showPillInfo(any())
    }

    @Test
    fun `Show error message after loading error`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        pillSubject.onError(RuntimeException())

        verify(view).showErrorMessage()
    }

    @Test
    fun `Close activity after loading error`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        pillSubject.onError(RuntimeException())

        verify(view).close()
    }

    @Test
    fun `Don't show results and error message after loading interruption`() {
        presenter.loadPillInfo(TestData.getSecondPill().id)
        presenter.onDestroy()

        verify(view, never()).showErrorMessage()
        verify(view, never()).showPillInfo(any())
    }


    @Test
    fun `Get information about name during saving a pill`() {
        presenter.savePill()

        verify(view).getPillName()
    }
}