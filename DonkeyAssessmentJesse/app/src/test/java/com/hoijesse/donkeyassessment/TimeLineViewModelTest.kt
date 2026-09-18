package com.hoijesse.donkeyassessment

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.hoijesse.donkeyassessment.data.TimeLineRepository
import com.hoijesse.donkeyassessment.ui.TimeLineViewModel
import junit.framework.TestCase.assertEquals
import org.junit.Rule
import org.junit.Test

class TimeLineViewModelTest {
    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private val repository = TimeLineRepository()
    private val viewModel = TimeLineViewModel(repository)

    @Test
    fun `initPage loads 20 entries`() {
        viewModel.initPage()
        val entries = viewModel.entries.value
        assertEquals(20, entries?.size)
    }

    @Test
    fun `no double initPage`() {
        viewModel.initPage()
        viewModel.initPage()
        val entries = viewModel.entries.value
        assertEquals(20, entries?.size)
    }

    @Test
    fun `nextPage adds 20 more entries`() {
        viewModel.initPage()
        viewModel.nextPage()
        val entries = viewModel.entries.value
        assertEquals(40, entries?.size)
    }

    @Test
    fun `nextPage loads all`() {
        viewModel.initPage()
        repeat(74) { viewModel.nextPage() }
        val entries = viewModel.entries.value
        assertEquals(1200, entries?.size)
    }

    @Test
    fun `no more after end`() {
        viewModel.initPage()
        repeat(100) { viewModel.nextPage() }
        val entries = viewModel.entries.value
        assertEquals(1200, entries?.size)
    }
}