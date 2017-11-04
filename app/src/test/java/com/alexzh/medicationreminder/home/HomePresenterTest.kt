@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.data.PillsRepository
import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.whenever
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.never
import io.reactivex.schedulers.Schedulers
import io.reactivex.subjects.SingleSubject
import org.junit.Test

class HomePresenterTest {

    companion object {
        val PILL_NAME = "pill"
        val PILL_DESCRIPTION = "pill description"
    }
    private val pills = listOf(Pill(PILL_NAME, PILL_DESCRIPTION))

    private val pillsSubject = SingleSubject.create<List<Pill>>()

    private val homeView = mock<Home.View>()
    private val repository = mock<PillsRepository>().apply {
        whenever(this.getMorePills()).thenReturn(pillsSubject)
    }

    private val homePresenter = HomePresenter(homeView, repository, Schedulers.trampoline(), Schedulers.trampoline())

    @Test
    fun `Show loader during loading additional data`() {
        homePresenter.loadMore()
        verify(homeView).showLoader()
    }

    @Test
    fun `Call repository during loading additional data`() {
        homePresenter.loadMore()
        verify(repository).getMorePills()
    }

    @Test
    fun `Show results after loading the pills`() {
        homePresenter.loadMore()
        pillsSubject.onSuccess(pills)
        verify(homeView).showPills(any())
    }

    @Test
    fun `Show error message after loading error`() {
        homePresenter.loadMore()
        pillsSubject.onError(RuntimeException())
        verify(homeView).showLoadingError()
    }

    @Test
    fun `Don't show error message after success loading`() {
        homePresenter.loadMore()
        pillsSubject.onSuccess(pills)
        verify(homeView, never()).showLoadingError()
    }

    @Test
    fun `Hide loader after loading error`() {
        homePresenter.loadMore()
        pillsSubject.onError(RuntimeException())
        verify(homeView).hideLoader()
    }

    @Test
    fun `Hide loader after success loading`() {
        homePresenter.loadMore()
        pillsSubject.onSuccess(pills)
        verify(homeView).hideLoader()
    }

    @Test
    fun `Hide loading after interruption of loading additional data`() {
        homePresenter.loadMore()
        homePresenter.onDestroy()
        pillsSubject.onError(RuntimeException())
        verify(homeView).hideLoader()
    }
}