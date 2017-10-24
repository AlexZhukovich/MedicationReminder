@file:Suppress("IllegalIdentifier")

package com.alexzh.medicationreminder.home

import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test

class HomePresenterTest {

    private val homeView = mock<Home.View>()

    private val homePresenter = HomePresenter(homeView)

    @Test
    fun `Show loader during loading additional data`() {
        homePresenter.loadMore()
        verify(homeView).showLoader()
    }
}