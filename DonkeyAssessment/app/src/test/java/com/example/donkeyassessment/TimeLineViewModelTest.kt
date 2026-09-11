package com.example.donkeyassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.donkeyassessment.data.TimeLineRepository
import com.example.donkeyassessment.ui.TimeLineViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class TimeLineViewModelTest {
    @get:Rule
    val instantTastExecutorRule = InstantTaskExecutorRule()

    private val repo = TimeLineRepository()
    private val viewModel = TimeLineViewModel(repo)

    @Test
    fun loadFirstPage_twentyItems(){
        viewModel.firstPage()

        val items = viewModel.items.value

        assertEquals(20, items?.size)
    }

    @Test
    fun loadNextPage_addsTwenty() {
        viewModel.firstPage()
        viewModel.nextPage()

        val items = viewModel.items.value

        assertEquals(40, items?.size)
    }

    @Test
    fun noSecondFirstPage() {
        viewModel.firstPage()
        viewModel.firstPage()

        val items = viewModel.items.value
        assertEquals(20, items?.size)
    }

    @Test
    fun nextPage_loadsTillEnd() {
        viewModel.firstPage()

        repeat(49) {
            viewModel.nextPage()
        }

        val items = viewModel.items.value

        assertEquals(600, items?.size)
    }

    @Test
    fun nextPage_afterEndNoMoreItems() {
        viewModel.firstPage()

        repeat(60) {
            viewModel.nextPage()
        }

        val items = viewModel.items.value

        assertEquals(600, items?.size)
    }
}