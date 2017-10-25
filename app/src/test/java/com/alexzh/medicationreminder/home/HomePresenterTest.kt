@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import com.alexzh.medicationreminder.data.model.Pill
import com.nhaarman.mockito_kotlin.*
import io.reactivex.subjects.SingleSubject
import org.junit.Test

class HomePresenterTest {

    companion object {
        val PILL_NAME = "pill"
        val PILL_DESCRIPTION = "pill description"
    }
    private val pill = Pill(PILL_NAME, PILL_DESCRIPTION)

    private val pillsSubject = SingleSubject.create<List<Pill>>()

    private val homeView = mock<Home.View>()
    private val repository = spy<Home.Repository>().apply {
        whenever(this.getMorePills()).thenReturn(pillsSubject)
    }

    private val homePresenter = HomePresenter(homeView, repository)

    @Test
    fun `Show loader during loading additional data`() {
        homePresenter.loadMore()
        verify(homeView).showLoader()
    }

    @Test
    fun `Hide loader after loading additional data`() {
        homePresenter.loadMore()
        verify(homeView).hideLoader()
    }

    @Test
    fun `Call repository during loading additional data`() {
        homePresenter.loadMore()
        verify(repository).getMorePills()
    }

    @Test
    fun `Show results after loading the pills`() {
        homePresenter.loadMore()
        pillsSubject.onSuccess(listOf(pill))
        verify(homeView).showPills(any())
    }
}