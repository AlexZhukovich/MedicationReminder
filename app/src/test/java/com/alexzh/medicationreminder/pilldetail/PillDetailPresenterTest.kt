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
import com.nhaarman.mockito_kotlin.verifyZeroInteractions
import com.nhaarman.mockito_kotlin.verifyNoMoreInteractions
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import org.junit.Test
import java.lang.RuntimeException
import io.reactivex.Completable

class PillDetailPresenterTest {

    companion object {
        val EMPTY_TEXT = ""
    }

    private val pillSubject = SingleSubject.create<Pill>()

    private val view = mock<PillDetail.View>()
    private val repository = mock<PillsRepository>().apply {
        whenever(this.getPillById(any())).thenReturn(pillSubject)
        whenever(this.savePill(any())).thenReturn(Completable.complete())
        whenever(this.updatePill(any())).thenReturn(Completable.complete())
    }

    private val presenter = PillDetailPresenter(view, repository, Schedulers.trampoline(), Schedulers.trampoline())

    @Test
    fun `Call repository during loading data with pill ID`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        verify(repository).getPillById(TestData.getFirstPill().id)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Display pill info after loading`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        pillSubject.onSuccess(TestData.getFirstPill())

        verify(view).showPillInfo(any())
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `Show error message and close activity after loading error`() {
        presenter.loadPillInfo(TestData.getFirstPill().id)

        pillSubject.onError(RuntimeException())

        verify(view).showErrorMessage()
        verify(view).close()
        verifyNoMoreInteractions(view)
    }

    @Test
    fun `Don't show results and error message after loading interruption`() {
        presenter.loadPillInfo(TestData.getSecondPill().id)
        presenter.onDestroy()

        verify(view, never()).showErrorMessage()
        verify(view, never()).showPillInfo(any())
    }

    @Test
    fun `Get information about pill during saving a pill`() {
        whenever(view.getPillName()).thenReturn(TestData.getFirstPill().name)
        whenever(view.getPillDescription()).thenReturn(TestData.getFirstPill().description)
        whenever(view.getPillDosage()).thenReturn(TestData.getFirstPill().dosage)

        presenter.savePill()

        verify(view).getPillName()
        verify(view).getPillDescription()
        verify(view).getPillDosage()
    }

    @Test
    fun `Call repository during saving new pill`() {
        val pill = TestData.getFirstPill().apply { id = 0 }

        whenever(view.getPillName()).thenReturn(pill.name)
        whenever(view.getPillDescription()).thenReturn(pill.description)
        whenever(view.getPillDosage()).thenReturn(pill.dosage)

        presenter.savePill()

        verify(repository).savePill(pill)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Don't call repository during saving pill with empty name and dosage`() {
        whenever(view.getPillName()).thenReturn(EMPTY_TEXT)
        whenever(view.getPillDescription()).thenReturn(TestData.getFirstPill().description)
        whenever(view.getPillDosage()).thenReturn(EMPTY_TEXT)

        presenter.savePill()

        verifyZeroInteractions(repository)
    }

    @Test
    fun `Don't call repository during saving pill with empty name`() {
        whenever(view.getPillName()).thenReturn(EMPTY_TEXT)
        whenever(view.getPillDescription()).thenReturn(TestData.getFirstPill().description)
        whenever(view.getPillDosage()).thenReturn(TestData.getFirstPill().dosage)

        presenter.savePill()

        verifyZeroInteractions(repository)
    }

    @Test
    fun `Call repository during updating existing pill`() {
        val pill = TestData.getFirstUpdatedPill()

        whenever(view.getPillName()).thenReturn(pill.name)
        whenever(view.getPillDescription()).thenReturn(pill.description)
        whenever(view.getPillDosage()).thenReturn(pill.dosage)

        presenter.updatePill(TestData.FIRST_PILL_ID)

        verify(repository).updatePill(pill)
        verifyNoMoreInteractions(repository)
    }

    @Test
    fun `Don't call repository during updating empty pill`() {
        whenever(view.getPillName()).thenReturn(EMPTY_TEXT)
        whenever(view.getPillDescription()).thenReturn(EMPTY_TEXT)
        whenever(view.getPillDosage()).thenReturn(EMPTY_TEXT)

        presenter.updatePill(TestData.FIRST_PILL_ID)

        verifyZeroInteractions(repository)
    }
}